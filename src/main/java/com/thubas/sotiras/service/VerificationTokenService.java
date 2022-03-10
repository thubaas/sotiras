package com.thubas.sotiras.service;

import com.thubas.sotiras.model.User;
import com.thubas.sotiras.model.VerificationToken;

public interface VerificationTokenService {
	
	void deleteToken(String token);
	
	VerificationToken getToken(String token);
	
	VerificationToken createToken(User user);
	
	void verifyUser(VerificationToken verificationToken);
	
	boolean isValid(VerificationToken verificationToken); 

}
