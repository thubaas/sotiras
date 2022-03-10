package com.thubas.sotiras.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.thubas.sotiras.dto.SigninRequest;
import com.thubas.sotiras.dto.SignupDto;
import com.thubas.sotiras.dto.UserDto;
import com.thubas.sotiras.mapper.UserMapper;
import com.thubas.sotiras.model.ERole;
import com.thubas.sotiras.model.Role;
import com.thubas.sotiras.model.User;
import com.thubas.sotiras.repository.RoleRepository;
import com.thubas.sotiras.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder encoder;
	private final UserMapper userMapper;

	@Override
	public UserDto register(SignupDto signupDto) {
		
		User user = new User();
		user.setEmail(signupDto.getEmail());
		user.setUsername(signupDto.getUsername());
		user.setPassword(encoder.encode(signupDto.getPassword()));
		Role userRole = roleRepository.findByName(ERole.ROLE_USER).get();
		user.getRoles().add(userRole);
		User registeredUser = userRepository.save(user);
		return userMapper.map(registeredUser);
		
	}

	@Override
	public UserDto login(SigninRequest signinRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword())
		);		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User authenticatedUser = userRepository.findByEmail(signinRequest.getEmail()).get();
		UserDto userDto = userMapper.map(authenticatedUser);
		return userDto;
	}

	@Override
	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}
	
	@Override
	public boolean doesEmailExist(String email) {
		return userRepository.existsByEmail(email);
	}
	
	@Override
	public boolean doesUsernameExist(String username) {
		return userRepository.existsByUsername(username);
	}
	
	@Override
	public User getAuthenticatedUser(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email));
		user.setPassword(null);
		return user;
	}

	@Override
	public boolean isAccountEnabled(String email) {
		return userRepository.findByEmail(email).get().getIsEnabled();
	}

	@Override
	public User getAuthenticatedUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("Authenticated Email: {}", email);
		return userRepository.findByUsername(email).get();
	}

}
