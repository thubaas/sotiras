package com.thubas.sotiras.dto;

import java.util.ArrayList;
import java.util.List;

import com.thubas.sotiras.model.BookCondition;
import com.thubas.sotiras.model.BookStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
	
	private Long bookId;
	private String title;
	private String description;
	private String publisher;
	private Double price;
	private String imageUrl;
	private List<String> authors = new ArrayList<>();
	private List<String> categories = new ArrayList<>();
	private String username;
	private String userImageUrl;
	private BookStatus status;
	private BookCondition condition;
	private Boolean canSwap;
	private String creationDate;
	private Integer upvotesCount;
	private Integer downvotesCount;
	private Integer reviewsCount;

}
