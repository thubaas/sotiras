package com.thubas.sotiras.service;

import java.util.Collection;

import com.thubas.sotiras.dto.ReviewDto;

public interface ReviewService {
	
	ReviewDto createReview(ReviewDto reviewDto);
	
	ReviewDto getReview(Long id);
	
	Collection<ReviewDto> getReviewsByBook(Long bookId);
	
	void deleteReview(Long id);

}
