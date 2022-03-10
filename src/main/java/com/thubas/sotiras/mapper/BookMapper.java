package com.thubas.sotiras.mapper;

import static com.thubas.sotiras.model.VoteType.DOWNVOTE;
import static com.thubas.sotiras.model.VoteType.UPVOTE;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ocpsoft.prettytime.PrettyTime;

import com.thubas.sotiras.dto.BookDto;
import com.thubas.sotiras.model.Book;
import com.thubas.sotiras.model.Review;
import com.thubas.sotiras.model.User;
import com.thubas.sotiras.model.Vote;

@Mapper(componentModel = "spring")
public abstract class BookMapper {
	
	private PrettyTime p = new PrettyTime();
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "title", source = "bookDto.title")
	@Mapping(target = "imageUrl", source = "bookDto.imageUrl")
	@Mapping(target = "authors", source = "bookDto.authors")
	@Mapping(target = "categories", source = "bookDto.categories")
	@Mapping(target = "status", source = "bookDto.status")
	@Mapping(target = "price", source = "bookDto.price")
	@Mapping(target = "condition", source = "bookDto.condition")
	@Mapping(target = "canSwap", source = "bookDto.canSwap")
	@Mapping(target = "contributor", source = "contributor")
	@Mapping(target = "creationDate", source = "bookDto.creationDate")
	@Mapping(target = "reviews", ignore = true)
	@Mapping(target = "votes", ignore = true)
	public abstract Book map(BookDto bookDto, User contributor);
	
	@Mapping(target = "bookId", expression = "java(book.getId())")
	@Mapping(target = "imageUrl", expression = "java(book.getImageUrl())")
	@Mapping(target = "publisher", expression = "java(book.getPublisher())")
	@Mapping(target = "description", expression = "java(book.getDescription())")
	@Mapping(target = "price", expression = "java(book.getPrice())")
	@Mapping(target = "canSwap", expression = "java(book.getCanSwap())")
	@Mapping(target = "condition", expression = "java(book.getCondition())")
	@Mapping(target = "username", expression = "java(book.getContributor().getUsername())")
	@Mapping(target = "userImageUrl", expression = "java(book.getContributor().getImageUrl())")
	@Mapping(target = "creationDate", expression = "java(createPrettyTime(book.getCreationDate()))")
	@Mapping(target = "upvotesCount", expression = "java(getUpvotesCount(book))")
	@Mapping(target = "downvotesCount", expression = "java(getDownvotesCount(book))")
	@Mapping(target = "reviewsCount", expression = "java(getReviewsCount(book))")
	public abstract BookDto map(Book book);
	
	protected String createPrettyTime(Instant instant) {
		return p.format(Date.from(instant));	
	}
	
	protected int getUpvotesCount(Book book) {
		Collection<Vote> votes = book.getVotes();
		return votes.stream()
				.filter(vote -> vote.getVoteType().equals(UPVOTE))
				.collect(Collectors.toList())
				.size();
	}
	
	protected int getDownvotesCount(Book book) {
		Collection<Vote> votes = book.getVotes();
		return votes.stream()
				.filter(vote -> vote.getVoteType().equals(DOWNVOTE))
				.collect(Collectors.toList())
				.size();
		
	}
	
	protected int getReviewsCount(Book book) {
		Collection<Review> reviews = book.getReviews();
		return reviews.size();
	}
}
