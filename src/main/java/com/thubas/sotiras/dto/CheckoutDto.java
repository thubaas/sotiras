package com.thubas.sotiras.dto;

import java.time.Instant;

import com.thubas.sotiras.model.CheckoutStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDto {
	
	private Long checkoutId;
	private Long bookId;
	private String bookTitle;
	private String username;
	private String userImageUrl;
	private Instant time;
	private CheckoutStatus status;

}
