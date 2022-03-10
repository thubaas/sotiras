package com.thubas.sotiras.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
	
	private Long id;
	private Long userId;
	private Long bookId;
	private String voteType;
}
