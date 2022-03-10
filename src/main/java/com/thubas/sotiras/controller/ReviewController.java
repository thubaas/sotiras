package com.thubas.sotiras.controller;

import static java.time.LocalDateTime.now;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thubas.sotiras.dto.ReviewDto;
import com.thubas.sotiras.model.Response;
import com.thubas.sotiras.service.ReviewService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@RestController
@RequestMapping("/bookman/reviews")
public class ReviewController {
	
	private final ReviewService reviewService;
	
	
	@PostMapping
	public ResponseEntity<Response> reviewBook(@RequestBody @Valid ReviewDto reviewDto) {
		ReviewDto createdReview = reviewService.createReview(reviewDto);
		
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.data(Map.of("review", createdReview))
				.message("Review Added Successfully")
				.status(HttpStatus.CREATED)
				.statusCode(HttpStatus.CREATED.value())
				.build()
				);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response> getReview(@PathVariable Long id) {
		ReviewDto reviewDto = reviewService.getReview(id);
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.message("Review retrieved successfully")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.data(Map.of("review", reviewDto))
				.build()
			);
	}
	
	@GetMapping("/by-book/{bookId}")
	public ResponseEntity<Response> getReviews(@PathVariable Long bookId) {
		Collection<ReviewDto> reviewDtos = reviewService.getReviewsByBook(bookId);
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.message("Review retrieved successfully")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.data(Map.of("review", reviewDtos))
				.build()
			);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteReview(@PathVariable Long id) {

		reviewService.deleteReview(id);
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.message("Review deleted")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.build()
				);
	}

}
