package com.thubas.sotiras.service;

import java.util.Collection;

import com.thubas.sotiras.dto.CheckoutDto;

public interface CheckoutService {
	
	CheckoutDto requestBook(Long bookId);
	CheckoutDto approveRequest(Long id);
	CheckoutDto getRequest(Long id);
	Boolean deleteRequest(Long id);
	Collection<CheckoutDto> getRequests(Integer limit);

}
