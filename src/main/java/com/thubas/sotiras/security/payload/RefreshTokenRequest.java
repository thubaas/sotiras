package com.thubas.sotiras.security.payload;

import javax.validation.constraints.NotBlank;

public class RefreshTokenRequest {
	
	@NotBlank(message = "Refresh token cannot be blank")
	private String refreshToken;

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
