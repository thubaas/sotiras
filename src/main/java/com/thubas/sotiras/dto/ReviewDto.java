package com.thubas.sotiras.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
	
	private Long reviewId;
	private String message;
	private String time;
	private String username;
	private String userImageUrl;
	private Long bookId;
}
