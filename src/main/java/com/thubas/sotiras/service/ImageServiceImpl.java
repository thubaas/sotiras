package com.thubas.sotiras.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.thubas.sotiras.dto.BookDto;
import com.thubas.sotiras.dto.UserDto;
import com.thubas.sotiras.mapper.BookMapper;
import com.thubas.sotiras.mapper.UserMapper;
import com.thubas.sotiras.model.Book;
import com.thubas.sotiras.model.User;
import com.thubas.sotiras.repository.BookRepository;
import com.thubas.sotiras.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ImageServiceImpl implements ImageService{
	
	private final BookRepository bookRepository;
	private final AuthService authService;
	private final Cloudinary cloudinary;
	private final BookMapper bookMapper;
	private final UserMapper userMapper;
	private final UserRepository userRepository;
	
	@Override
	public BookDto saveBookImage(Long bookId, MultipartFile bookImage) {
		
		try {
			
			File uploadFile = convertMultipartToFile(bookImage);
			Map<?, ?> uploadResult = cloudinary.uploader().upload(uploadFile, ObjectUtils.emptyMap());
			String imageUrl = uploadResult.get("url").toString();
			Book book = bookRepository.findById(bookId).get();
			book.setImageUrl(imageUrl);
			Book savedBook = bookRepository.save(book);
			return bookMapper.map(savedBook);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserDto saveUserImage(MultipartFile userImage) {
		
		try {
			
			File uploadFile = convertMultipartToFile(userImage);
			Map<?, ?> uploadResult = cloudinary.uploader().upload(uploadFile, ObjectUtils.emptyMap());
			String imageUrl = uploadResult.get("url").toString();
			User user = authService.getAuthenticatedUser();
			user.setImageUrl(imageUrl);
			User savedUser = userRepository.save(user);
			return userMapper.map(savedUser);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private File convertMultipartToFile(MultipartFile file) throws IOException {
		File convertedFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convertedFile);
		fos.write(file.getBytes());
		fos.close();
		return convertedFile;
	}

}
