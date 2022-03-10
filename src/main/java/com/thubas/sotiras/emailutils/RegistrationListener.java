package com.thubas.sotiras.emailutils;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class RegistrationListener implements ApplicationListener<OnRegisteredEvent> {
	
	private final MailService mailService;
	
	@Override
	public void onApplicationEvent(OnRegisteredEvent event) {
		mailService.sendConfirmationEmail(event);
	}

}
