package com.thubas.sotiras.emailutils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
public class MailService {

	private final JavaMailSender mailSender;

	public void sendConfirmationEmail(OnRegisteredEvent event) {

		try {

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

			String content = String.format(
					"Thank you for joining BookMan. Please click on the link below to activate your account.\n%s%s",
					event.getAppUrl(), event.getToken());
			helper.setFrom(event.getEMAIL_FROM());
			helper.setTo(event.getEmailTo());
			helper.setSubject("Please Confirm your Registration.");
			mimeMessage.setContent(content, "text/html");
			mailSender.send(mimeMessage);
			log.info("Registration Confirmation Email sent to {}", event.getEmailTo());

		} catch (MessagingException e) {
			log.error("Error Sending Email: {}", e);
		}
	}

	public void sendRequestEmail(OnBookRequestedEvent event) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			String content = String.format(
					"Your book titled '%s' has  been requested.\n"
							+ "Please initiate negotiations with your client in this email address: %s",
					event.getBookTitle(), event.getClientContact());
			helper.setFrom(event.getEMAIL_FROM());
			helper.setTo(event.getEmailTo());
			helper.setSubject("New Book Request");
			mimeMessage.setContent(content, "text/html");
			mailSender.send(mimeMessage);
			log.info("Book request notification sent to : {}", event.getEmailTo());
		} catch (MessagingException e) {
			log.error("Error Sending Email: {}", e);
		}
	}

}
