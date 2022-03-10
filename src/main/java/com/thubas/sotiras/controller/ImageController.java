package com.thubas.sotiras.controller;

import static java.time.LocalDateTime.*;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thubas.sotiras.dto.BookDto;
import com.thubas.sotiras.dto.UserDto;
import com.thubas.sotiras.model.Response;
import com.thubas.sotiras.service.ImageService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@RequestMapping("/bookman/images")
@RestController
public class ImageController {
	
	private final ImageService imageService;
	
	@PostMapping("/books/{bookId}")
	public ResponseEntity<Response> setBookImage(
			@PathVariable Long bookId, 
			@RequestParam("bookImage") MultipartFile imageFile) {
		
		BookDto bookDto = imageService.saveBookImage(bookId, imageFile);
		return ResponseEntity.ok(
				Response.builder()
				.message("Book image updated successfully")
				.timeStamp(now())
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.data(Map.of("book", bookDto))
				.build()
				);
		
	}
	
	
	@PostMapping("/user")
	public ResponseEntity<Response> setProfileImage(@RequestParam("profileImage") MultipartFile imageFile) {
		UserDto userDto = imageService.saveUserImage(imageFile);
		return ResponseEntity.ok(
				Response.builder()
				.message("User image updated successfully")
				.timeStamp(now())
				.status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value())
				.data(Map.of("book", userDto))
				.build()
				);
	}
	
	

}
