package com.thubas.sotiras.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDto {
	
	private Long refreshTokenId;
	private String refreshToken;
	private String username;
	private Instant expiryDate;

}
