package com.csvemail.project.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.csvemail.project.model.Email;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendEmail(Email emailMessage) {

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		simpleMailMessage.setFrom(emailMessage.getFrom());
		simpleMailMessage.setTo(emailMessage.getTo().toArray(new String[emailMessage.getTo().size()]));
		simpleMailMessage.setSubject(emailMessage.getSubject());
		simpleMailMessage.setText(emailMessage.getBody());

		javaMailSender.send(simpleMailMessage);
	}

}
