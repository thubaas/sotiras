package com.thubas.sotiras.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.thubas.sotiras.dto.CheckoutDto;
import com.thubas.sotiras.model.Book;
import com.thubas.sotiras.model.Checkout;
import com.thubas.sotiras.model.User;

@Mapper(componentModel = "spring")
public interface CheckoutMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "book", source = "book")
	@Mapping(target = "user", source = "user")
	@Mapping(target = "time", source = "checkoutDto.time")
	@Mapping(target = "status", source = "checkoutDto.status")
	Checkout map(CheckoutDto checkoutDto, Book book, User user);
	
	@Mapping(target = "checkoutId", source = "checkout.id")
	@Mapping(target = "bookId", expression = "java(checkout.getBook().getId())")
	@Mapping(target = "bookTitle", expression = "java(checkout.getBook().getTitle())")
	@Mapping(target = "username", expression = "java(checkout.getUser().getUsername())")
	@Mapping(target = "userImageUrl", expression = "java(checkout.getUser().getImageUrl())")
	@Mapping(target = "time", source = "checkout.time")
	@Mapping(target = "status", source = "checkout.status")
	CheckoutDto map(Checkout checkout);

}
