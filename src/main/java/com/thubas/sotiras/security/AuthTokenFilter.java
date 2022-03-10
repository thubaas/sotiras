package com.thubas.sotiras.security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thubas.sotiras.security.service.UserDetailsServiceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter{
	
	private final JwtUtils jwtUtils;
	private final UserDetailsServiceImpl userDetailsService;
	private final String BEARER_STRING = "Bearer ";
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {
		
		final String SIGNIN_PATH = "/bookman/auth/signin";
		final String TOKEN_REFRESH_PATH = "/bookman/auth/refresh-token";
		final String REG_CONFIRMATION_PATH = "/bookman/auth/confirm-registration";
		log.info("Authtoken Filter running");
		String path = request.getServletPath();		
		if(path.equals(SIGNIN_PATH) || path.equals(TOKEN_REFRESH_PATH) || path.startsWith(REG_CONFIRMATION_PATH)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String authHeader = request.getHeader(AUTHORIZATION);
		if(authHeader != null && authHeader.startsWith(BEARER_STRING)) {
			try {
				
				String accessToken = authHeader.substring(BEARER_STRING.length());
				jwtUtils.validateJwtToken(accessToken);
				String username = jwtUtils.getUserNameFromJwtToken(accessToken);
				log.info("Username: {}", username);
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				log.info("User: {}", userDetails.getUsername());
				UsernamePasswordAuthenticationToken authentication = 
						new UsernamePasswordAuthenticationToken(
								userDetails, 
								null, 
								userDetails.getAuthorities()
						);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				
			} catch (Exception e) {
				log.error("Authetication Error: {}", e);
				response.setHeader("error", e.getMessage());
				response.setStatus(FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
				error.put("error_message", e.getMessage());
				response.setContentType(APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		}
				
		filterChain.doFilter(request, response);
		
	}
	
}
