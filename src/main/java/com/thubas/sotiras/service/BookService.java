package com.thubas.sotiras.service;

import java.util.Collection;

import com.thubas.sotiras.dto.BookDto;

public interface BookService {
	
	BookDto createBook(BookDto bookDto);
	Collection<BookDto> list(int limit);
	BookDto get(Long id);
	BookDto update(BookDto bookDto);
	Boolean delete(Long id);
	
}
