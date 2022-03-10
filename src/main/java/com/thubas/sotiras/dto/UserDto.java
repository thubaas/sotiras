package com.thubas.sotiras.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private Long userId;
	private String email;
	private String username;
	private String imageUrl;
	private List<String> roles = new ArrayList<>();

}
