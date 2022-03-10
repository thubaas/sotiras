package com.thubas.sotiras.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thubas.sotiras.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	Boolean existsByUsername(String username);
	

}
