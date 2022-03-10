package com.thubas.sotiras.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SigninResponse {
	
	private Long userId;
	private String token;
	private String refreshToken;
	private String username;
	private String imageUrl;
	private List<String> roles = new ArrayList<>();
}
