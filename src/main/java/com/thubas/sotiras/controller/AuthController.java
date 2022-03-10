package com.thubas.sotiras.controller;

import static java.time.LocalDateTime.now;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thubas.sotiras.dto.SigninRequest;
import com.thubas.sotiras.dto.SignupDto;
import com.thubas.sotiras.dto.UserDto;
import com.thubas.sotiras.emailutils.OnRegisteredEvent;
import com.thubas.sotiras.mapper.UserMapper;
import com.thubas.sotiras.model.RefreshToken;
import com.thubas.sotiras.model.Response;
import com.thubas.sotiras.model.User;
import com.thubas.sotiras.model.VerificationToken;
import com.thubas.sotiras.security.JwtConfig;
import com.thubas.sotiras.security.JwtUtils;
import com.thubas.sotiras.security.payload.RefreshTokenRequest;
import com.thubas.sotiras.service.AuthService;
import com.thubas.sotiras.service.RefreshTokenService;
import com.thubas.sotiras.service.VerificationTokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bookman/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final JwtUtils jwtUtils;
	private final RefreshTokenService refreshTokenService;
	private final ApplicationEventPublisher eventPublisher;
	private final VerificationTokenService verificationTokenService;
	private final UserMapper userMapper;
	private final JwtConfig jwtConfig;
	
	
	@PostMapping("/signup")
	public ResponseEntity<Response> registerUser(@RequestBody @Valid SignupDto signupDto) {
		if (authService.doesEmailExist(signupDto.getEmail())) {
			return ResponseEntity.badRequest()
					.body(
							Response.builder()
							.timeStamp(now())
							.message("Error: Email already in use")
							.status(HttpStatus.BAD_REQUEST)
							.statusCode(HttpStatus.BAD_REQUEST.value())
							.build()
						);
		}

		if (authService.doesUsernameExist(signupDto.getUsername())) {
			return ResponseEntity.badRequest()
					.body(
							Response.builder()
							.timeStamp(now())
							.message("Error: Username already taken")
							.status(HttpStatus.BAD_REQUEST)
							.statusCode(HttpStatus.BAD_REQUEST.value())
							.build()
						);
		}

		UserDto registeredUser = authService.register(signupDto); 
		String appUrl = "http://localhost:8080/bookman/auth/confirm-registration/?token=";
		 
		String verificationToken = verificationTokenService
				.createToken(userMapper.map(registeredUser))
				.getToken();
		
		eventPublisher.publishEvent(new OnRegisteredEvent(appUrl, registeredUser.getEmail(), verificationToken));

		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.message("User registered successfully")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.data(Map.of("user", registeredUser))
				.build());
	}
	
	@Transactional
	@PostMapping("/confirm-registration")
	public ResponseEntity<Response> confirmRegistration(@RequestParam String token) {
		log.info("Conf Token Running");
		VerificationToken verificationToken =  verificationTokenService.getToken(token);
		
		if(!verificationTokenService.isValid(verificationToken)) {
			verificationTokenService.deleteToken(verificationToken.getToken());
			User registeredUser = verificationToken.getUser();
			String appUrl = "http://localhost:8080/sotiras/auth/confirm-registration/?token=";
			String newVerificationToken = verificationTokenService.createToken(registeredUser).getToken();
			eventPublisher.publishEvent(
					new OnRegisteredEvent(appUrl, registeredUser.getEmail(), newVerificationToken)
					);
			
			return ResponseEntity.ok(
					Response.builder()
					.timeStamp(now())
					.message("Your confirmation linked has expired. Please check your email for a new confirmation link.")
					.status(HttpStatus.FORBIDDEN)
					.statusCode(HttpStatus.FORBIDDEN.value())
					.build()
					);
		}
		
		verificationTokenService.verifyUser(verificationToken);
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.message("Account Confirmed Successfully")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.build()
				);
	}

	@PostMapping("/signin")
	public ResponseEntity<Response> authenticateUser(@RequestBody @Valid SigninRequest request) {
		
		if(!authService.isAccountEnabled(request.getEmail())) {
			return ResponseEntity.ok(
					Response.builder()
					.timeStamp(now())
					.message("User account disabled. Please check the confirmation email")
					.status(HttpStatus.FORBIDDEN)
					.statusCode(HttpStatus.FORBIDDEN.value())
					.build());
		}

		UserDto userDto = authService.login(request);
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDto.getEmail());
		String accessToken = jwtUtils.generateTokenFromUsername(userDto.getEmail());
	
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.message("User Authenticated")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.data(Map.of(
						"user", userDto, 
						"token", accessToken, 
						"refreshToken", refreshToken.getToken(),
						"tokenExpiration", Instant.now().plusMillis(jwtConfig.getTokenExpirationMs()).toEpochMilli())
					)
				.build());

	}

	@PostMapping("/refresh-token")
	public ResponseEntity<Response> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
		
		RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken());
		User user = refreshToken.getUser();
		if(!refreshTokenService.isValid(refreshToken)) {
			refreshTokenService.deleteByUserId(user.getId());
			return ResponseEntity.ok(
					Response.builder()
					.timeStamp(now())
					.message("Refresh token expired please signin again")
					.status(HttpStatus.FORBIDDEN)
					.statusCode(HttpStatus.FORBIDDEN.value())
					.build()
					);
		}
		
		String accessToken = jwtUtils.generateTokenFromUsername(user.getEmail());
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.message("Token refreshed successfully")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.data(Map.of("accessToken", accessToken, "refreshToken", refreshToken))
				.build()
				);
	}

	@PostMapping("/signout")
	public ResponseEntity<Response> logoutUser() {
		authService.logout();
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.message("You have successfully signed out")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.build());
	}

}
