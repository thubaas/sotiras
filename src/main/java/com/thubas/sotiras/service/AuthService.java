package com.thubas.sotiras.service;

import com.thubas.sotiras.dto.SigninRequest;
import com.thubas.sotiras.dto.SignupDto;
import com.thubas.sotiras.dto.UserDto;
import com.thubas.sotiras.model.User;

public interface AuthService {
	
	
	UserDto register(SignupDto signupDto);
	
	UserDto login(SigninRequest signinRequest);
	
	void logout();
	
	boolean doesEmailExist(String email);
	
	boolean doesUsernameExist(String username);
	
	User getAuthenticatedUser(String email);
	
	User getAuthenticatedUser();
	
	boolean isAccountEnabled(String email);
	
	

}
