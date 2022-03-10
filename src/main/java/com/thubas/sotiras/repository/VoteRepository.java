package com.thubas.sotiras.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thubas.sotiras.model.Book;
import com.thubas.sotiras.model.User;
import com.thubas.sotiras.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
	
	Vote findByBookAndUser(Book book, User user);
	Collection<Vote> findByBook(Book book);

}
