package com.thubas.sotiras.emailutils;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class BookRequestListener implements ApplicationListener<OnBookRequestedEvent>{
	
	private final MailService mailService;
	
	@Override
	public void onApplicationEvent(OnBookRequestedEvent event) {
		mailService.sendRequestEmail(event);
		
	}

}
