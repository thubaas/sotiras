package com.thubas.sotiras.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thubas.sotiras.model.Book;
import com.thubas.sotiras.model.Review;
import com.thubas.sotiras.model.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	Collection<Review> findByBook(Book book);
	Review findByUser(User user);

}
