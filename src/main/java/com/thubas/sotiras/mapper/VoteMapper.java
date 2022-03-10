package com.thubas.sotiras.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.thubas.sotiras.dto.VoteDto;
import com.thubas.sotiras.model.Book;
import com.thubas.sotiras.model.User;
import com.thubas.sotiras.model.Vote;

@Mapper(componentModel = "spring")
public abstract class VoteMapper {
		
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "book", source = "book")
	@Mapping(target = "user", source = "user")
	@Mapping(target = "voteType", source = "voteDto.voteType")
	public abstract Vote map(VoteDto voteDto, User user, Book book);
	
	@Mapping(target = "id", source = "vote.id")
	@Mapping(target = "userId", expression = "java(vote.getUser().getId())")
	@Mapping(target = "bookId", expression = "java(vote.getBook().getId())")
	@Mapping(target = "voteType", expression = "java(vote.getVoteType().name())")
	public abstract VoteDto map(Vote vote);
	
}
