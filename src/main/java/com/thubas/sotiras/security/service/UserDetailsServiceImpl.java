package com.thubas.sotiras.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thubas.sotiras.model.User;
import com.thubas.sotiras.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		String msg = String.format("USERNAME %s NOT FOUND", email);
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(msg));
		return UserDetailsImpl.build(user);
	}

}
