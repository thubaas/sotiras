package com.thubas.sotiras.service;

import org.springframework.web.multipart.MultipartFile;

import com.thubas.sotiras.dto.BookDto;
import com.thubas.sotiras.dto.UserDto;

public interface ImageService {
	
	BookDto saveBookImage(Long bookId, MultipartFile bookImage);
	
	UserDto saveUserImage(MultipartFile userImage);

}
