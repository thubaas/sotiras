package com.thubas.sotiras.controller;

import static java.time.LocalDateTime.now;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thubas.sotiras.dto.BookDto;
import com.thubas.sotiras.model.Response;
import com.thubas.sotiras.service.BookService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bookman/books")
@RequiredArgsConstructor
public class BookController {
	
	private final BookService bookService;
	
	@GetMapping
	public ResponseEntity<Response> getBooks() {
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.data(Map.of("books", bookService.list(30)))
				.message("Books retrieved")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.build()
			);
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping
	public ResponseEntity<Response> saveBook(@RequestBody @Valid BookDto bookDto) {
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.data(Map.of("book", bookService.createBook(bookDto)))
				.message("Book saved")
				.status(HttpStatus.CREATED)
				.statusCode(HttpStatus.CREATED.value())
				.build()
				);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response> getBook(@PathVariable Long id) {
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.data(Map.of("book", bookService.get(id)))
				.message("Book retrieved")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.build()
			);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response> updateBook(@RequestBody @Valid BookDto bookDto) {
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.data(Map.of("book", bookService.createBook(bookDto)))
				.message("Book updated")
				.status(HttpStatus.CREATED)
				.statusCode(HttpStatus.CREATED.value())
				.build()
				);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteBook(@PathVariable Long id) {
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.data(Map.of("deleted", bookService.delete(id)))
				.message("Book deleted")
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.build()
				);
		
	}
				
}
