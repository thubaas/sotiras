package com.thubas.sotiras.controller;

import static java.time.LocalDateTime.now;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thubas.sotiras.dto.VoteDto;
import com.thubas.sotiras.model.Response;
import com.thubas.sotiras.service.VoteService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@RestController
@RequestMapping("/bookman/votes")
public class VoteController {
	
	private final VoteService voteService;
	
	@PostMapping
	public ResponseEntity<Response> voteBook(@RequestBody VoteDto voteDto) {
		
		VoteDto savedVote = voteService.voteBook(voteDto);
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.message("Vote created")
				.status(HttpStatus.CREATED)
				.statusCode(HttpStatus.CREATED.value())
				.data(Map.of("vote", savedVote))
				.build()
				);
		
		
	}
}
