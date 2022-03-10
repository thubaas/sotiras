package com.thubas.sotiras.service;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.thubas.sotiras.dto.BookDto;
import com.thubas.sotiras.mapper.BookMapper;
import com.thubas.sotiras.model.Book;
import com.thubas.sotiras.model.BookStatus;
import com.thubas.sotiras.model.User;
import com.thubas.sotiras.repository.BookRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class BookServiceImpl implements BookService {
	
	private final BookRepository bookRepository;
	private final AuthService authService;
	private final BookMapper mapper;

	@Override
	public BookDto createBook(BookDto bookDto) {
		User user = authService.getAuthenticatedUser();
		Book book = mapper.map(bookDto, user);
		book.setCreationDate(Instant.now());
		book.setStatus(BookStatus.AVAILABLE);
		log.info("Creating new Book: {}", book.getTitle());
		Book savedBook = bookRepository.save(book);
		BookDto savedBookDto = mapper.map(savedBook);
		return savedBookDto;
	}

	@Override
	public Collection<BookDto> list(int limit) {
		log.info("Fetching books");
		Collection<Book> books = bookRepository.findAll(PageRequest.of(0, limit)).toList();
		Collection<BookDto> bookDtos = books.stream()
				.map(book -> mapper.map(book))
				.collect(Collectors.toList());
		return bookDtos;
	}

	@Override
	public BookDto get(Long id) {
		log.info("Fetching Book by ID : {}", id);
		Book book = bookRepository
				.findById(id)
				.get();
		BookDto bookDto = mapper.map(book);
		return bookDto;
	}

	@Override
	public BookDto update(BookDto bookDto) {
		log.info("Updating the Book : {}", bookDto.getTitle());
		User contributor = authService.getAuthenticatedUser();
		Book book = mapper.map(bookDto, contributor);
		Book savedBook = bookRepository.save(book);
		BookDto savedBookDto = mapper.map(savedBook);
		return savedBookDto;
	}

	@Override
	public Boolean delete(Long id) {
		log.info("Deleting the book by ID: {}", id);
		bookRepository.deleteById(id);
		return Boolean.TRUE;
	}

}
