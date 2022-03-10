package com.thubas.sotiras.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.thubas.sotiras.dto.UserDto;
import com.thubas.sotiras.model.Role;
import com.thubas.sotiras.model.User;
import com.thubas.sotiras.repository.UserRepository;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
	
	@Autowired
	private UserRepository userRepository;

	@Mapping(target = "userId", source = "user.id")
	@Mapping(target = "username", source = "user.username")
	@Mapping(target = "email", source = "user.email")
	@Mapping(target = "imageUrl", source = "user.imageUrl")
	@Mapping(target = "roles", expression = "java(getRoles(user))")
	public abstract UserDto map(User user);

	@Mapping(target = "id", source = "userDto.userId")
	@Mapping(target = "username", source = "userDto.username")
	@Mapping(target = "email", source = "userDto.email")
	@Mapping(target = "imageUrl", ignore = true)
	@Mapping(target = "isEnabled", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "roles", expression = "java(getRoles(userDto.getEmail()))")
	public abstract User map(UserDto userDto);

	List<String> getRoles(User user) {
		List<String> roles = user.getRoles().stream()
				.map(role -> role.getName().name())
				.collect(Collectors.toList());
		return roles;
	}
	
	Collection<Role> getRoles(String email) {
		return userRepository.findByEmail(email)
				.get()
				.getRoles();
	}

}
