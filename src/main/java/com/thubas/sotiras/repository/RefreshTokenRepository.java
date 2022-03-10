package com.thubas.sotiras.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thubas.sotiras.model.RefreshToken;
import com.thubas.sotiras.model.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	
	Optional<RefreshToken> findByToken(String token);
	
	void deleteByUser(User user);

}
