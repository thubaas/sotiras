package com.thubas.sotiras.emailutils;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnRegisteredEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;
	
	private String appUrl;
	private String emailTo;
	private final String EMAIL_FROM = "sotiras@mail.com";
	private String token;
	
	public OnRegisteredEvent(String appUrl, String emailTo, String token) {
		super(appUrl);
		this.appUrl = appUrl;
		this.emailTo = emailTo;
		this.token = token;
	}
	
	

}
