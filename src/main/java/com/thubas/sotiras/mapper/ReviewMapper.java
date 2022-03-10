package com.thubas.sotiras.mapper;

import java.time.Instant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ocpsoft.prettytime.PrettyTime;

import com.thubas.sotiras.dto.ReviewDto;
import com.thubas.sotiras.model.Book;
import com.thubas.sotiras.model.Review;
import com.thubas.sotiras.model.User;

@Mapper(componentModel = "spring")
public abstract class ReviewMapper {
	
	private PrettyTime p = new PrettyTime();
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "message", source = "reviewDto.message")
	@Mapping(target = "time", ignore = true)
	@Mapping(target = "user", source = "user")
	@Mapping(target = "book", source = "book")
	public abstract Review map(ReviewDto reviewDto, User user, Book book);
	
	@Mapping(target = "reviewId", source = "review.id")
	@Mapping(target = "message", source = "review.message")
	@Mapping(target = "time", expression = "java(createPrettyTime(review.getTime()))")
	@Mapping(target = "username", expression = "java(review.getUser().getUsername())")
	@Mapping(target = "userImageUrl", expression = "java(review.getUser().getImageUrl())")
	@Mapping(target = "bookId", expression = "java(review.getBook().getId())")
	public abstract ReviewDto map(Review review);
	
	protected String createPrettyTime(Instant instant) {
		return p.format(instant);
	}

}
