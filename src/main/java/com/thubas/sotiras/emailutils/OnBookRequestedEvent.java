package com.thubas.sotiras.emailutils;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnBookRequestedEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	
	private String appUrl;
	private String emailTo;
	private String clientContact;
	private String bookTitle;
	private final String EMAIL_FROM = "sotiras@mail.com";

	public OnBookRequestedEvent(String appUrl, String emailTo, String bookTitle) {
		super(appUrl);
		this.appUrl = appUrl;
		this.emailTo = emailTo;
		this.bookTitle = bookTitle;
	}

}
