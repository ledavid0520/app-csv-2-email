package com.csvemail.project.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.csvemail.project.model.Email;
import com.csvemail.project.model.User;
import com.csvemail.project.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public SimpleMailMessage template;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserService() {
	}

	public void sendEmail(Email emailMessage) {

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		simpleMailMessage.setFrom(emailMessage.getFrom());
		simpleMailMessage.setTo(emailMessage.getTo().toArray(new String[emailMessage.getTo().size()]));
		simpleMailMessage.setSubject(emailMessage.getSubject());
		simpleMailMessage.setText(emailMessage.getBody());

		javaMailSender.send(simpleMailMessage);
	}

	public String prepareEmails() {		
		

		List<User> users = userRepository.findAll();
		// List<String> emails = userRepository.findAll().stream().map(User::getEmail).collect(Collectors.toList());

		users.forEach(user -> {
			String email[] = {new StringBuilder().append(user.getName()).append(" <").append(user.getEmail()).append(">").toString()};
			sendEmail(new Email("EquipoDavid <testdavid2019@gmail.com>", 
						Arrays.stream(email).collect(Collectors.toList()),
						"Bienvenido a Equipo David", 
						new StringBuilder().append("Para ingresar use el siguiente password: ").append(user.getPassword()).toString()));
		});

		return "Password emails have been sent";
	}

}
