package com.thubas.sotiras.service;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.thubas.sotiras.dto.ReviewDto;
import com.thubas.sotiras.exception.SotirasException;
import com.thubas.sotiras.mapper.ReviewMapper;
import com.thubas.sotiras.model.Book;
import com.thubas.sotiras.model.Review;
import com.thubas.sotiras.model.User;
import com.thubas.sotiras.repository.BookRepository;
import com.thubas.sotiras.repository.ReviewRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
	
	private final ReviewRepository reviewRepository;
	private final ReviewMapper mapper;
	private final BookRepository bookRepository;
	private final AuthService authService;
	
	@Override
	public ReviewDto createReview(ReviewDto reviewDto) {
		User user = authService.getAuthenticatedUser();
		Review existingReview = reviewRepository.findByUser(user);
		if(existingReview != null) {
			throw new SotirasException("Only one review is allowed per user per book");
		}
		
		Book reviewedBook = bookRepository.findById(reviewDto.getBookId()).get();
		Review review = mapper.map(reviewDto, user, reviewedBook);
		review.setTime(Instant.now());
		log.info("Creating the review: {}", review);
		Review savedReview = reviewRepository.save(review);
		return mapper.map(savedReview);
	}
	
	@Override
	public ReviewDto getReview(Long id) {
		Review review = reviewRepository.findById(id).get();
		return mapper.map(review);
	}
	
	@Override
	public Collection<ReviewDto> getReviewsByBook(Long bookId) {
		Book book = bookRepository.findById(bookId).get();
		Collection<Review> reviews = reviewRepository.findByBook(book);
		Collection<ReviewDto> reviewDtos = reviews.stream()
				.map(review -> mapper.map(review))
				.collect(Collectors.toList());
		return reviewDtos;
	}

	@Override
	public void deleteReview(Long id) {
		reviewRepository.deleteById(id);
	}

}
