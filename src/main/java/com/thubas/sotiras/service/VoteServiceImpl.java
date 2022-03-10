package com.thubas.sotiras.service;

import org.springframework.stereotype.Service;

import com.thubas.sotiras.dto.VoteDto;
import com.thubas.sotiras.mapper.VoteMapper;
import com.thubas.sotiras.model.Book;
import com.thubas.sotiras.model.User;
import com.thubas.sotiras.model.Vote;
import com.thubas.sotiras.repository.BookRepository;
import com.thubas.sotiras.repository.VoteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class VoteServiceImpl implements VoteService {

	private final VoteRepository voteRepository;
	private final AuthService authService;
	private final BookRepository bookRepository;
	private final VoteMapper mapper;

	@Override
	public VoteDto voteBook(VoteDto voteDto) {
		
		User user = authService.getAuthenticatedUser();
		Book votedBook = bookRepository.findById(voteDto.getBookId()).get();
		
		Vote vote = mapper.map(voteDto, user, votedBook);
		
		Vote savedVote = getSavedVote(vote.getBook(), vote.getUser());
		if (savedVote == null) {
			savedVote = voteRepository.save(vote);
			return mapper.map(savedVote);
		}

		if (savedVote.getVoteType().equals(vote.getVoteType())) {
			return mapper.map(savedVote);
		}

		voteRepository.deleteById(savedVote.getId());
		savedVote = voteRepository.save(vote);
		return mapper.map(savedVote);
	}

	private Vote getSavedVote(Book book, User user) {
		Vote savedVote = voteRepository.findByBookAndUser(book, user);
		return savedVote;
	}

}
