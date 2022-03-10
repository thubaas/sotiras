package com.thubas.sotiras.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
	
	@NotBlank(message = "Username cannot be blank")
	private String username;
	@NotBlank(message = "Email cannot be blank")
	@Email
	private String email;
	@Min(8)
	@NotBlank(message = "Password cannot be blank")
	private String password;

}
