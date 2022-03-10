package com.thubas.sotiras.service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.thubas.sotiras.dto.CheckoutDto;
import com.thubas.sotiras.emailutils.OnBookRequestedEvent;
import com.thubas.sotiras.exception.SotirasException;
import com.thubas.sotiras.mapper.CheckoutMapper;
import com.thubas.sotiras.model.Book;
import com.thubas.sotiras.model.Checkout;
import com.thubas.sotiras.model.CheckoutStatus;
import com.thubas.sotiras.model.User;
import com.thubas.sotiras.repository.BookRepository;
import com.thubas.sotiras.repository.CheckoutRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class CheckoutServiceImpl implements CheckoutService {
	
	private final BookRepository bookRepository;
	private final CheckoutRepository checkoutRepository;
	private final AuthService authService;
	private final ApplicationEventPublisher eventPublisher;
	private final CheckoutMapper mapper;

	@Override
	public CheckoutDto requestBook(Long bookId) {
		log.info("Requesting the book ID : {}", bookId);
		
		Book book = bookRepository
				.findById(bookId)
				.get();
		
		if(authService.getAuthenticatedUser().getId().equals(book.getContributor().getId())){
			throw new SotirasException("A contributor cannot request their own book");
		}
		
		Checkout checkout = new Checkout();
		checkout.setBook(book);
		checkout.setStatus(CheckoutStatus.PENDING);
		checkout.setTime(Instant.now());
		checkout.setUser(authService.getAuthenticatedUser());
		bookRepository.save(book);
		//notify contributor by email
		
		eventPublisher.publishEvent(
				new OnBookRequestedEvent(
						"bookman.com", 
						book.getContributor().getEmail(), 
						book.getTitle()
						)
				);
		Checkout savedRequest = checkoutRepository.save(checkout);
		return mapper.map(savedRequest);
	}


	@Override
	public CheckoutDto getRequest(Long id) {
		log.info("Retrieving Checkout ID : {}", id);
		Checkout request = checkoutRepository
				.findById(id).get();
		if(!authService.getAuthenticatedUser().getId().equals(request.getBook().getContributor().getId())) {
			throw new SotirasException("Not authorized to view request");
		}
		return mapper.map(request);
	}

	@Override
	public Boolean deleteRequest(Long id) {
		log.info("Deleting Checkout ID : {}", id);
		checkoutRepository.deleteById(id);
		return Boolean.TRUE;
	}

	@Override
	public Collection<CheckoutDto> getRequests(Integer limit) {
		
		log.info("Retrieving checkouts");
		User authenticatedUser = authService.getAuthenticatedUser();
		List<Checkout> requests =  checkoutRepository
				.findByBook_Contributor_Id(authenticatedUser.getId());
		return requests.stream()
				.map(r -> mapper.map(r))
				.collect(Collectors.toList());
	}

	@Override
	public CheckoutDto approveRequest(Long id) {
		Checkout checkout = checkoutRepository.findById(id).get();
		User authenticatedUser = authService.getAuthenticatedUser();
		User bookOwner = checkout.getBook().getContributor();
		if(authenticatedUser.getId().compareTo(bookOwner.getId()) != 0) {
			log.error("Error Authenticated user does not own this book!");
			throw new SotirasException("Error Authenticated user does not own this book!");
		}
		checkout.setStatus(CheckoutStatus.PROCESSED);
		Checkout request = checkoutRepository.save(checkout);
		return mapper.map(request);
		
	}

}
