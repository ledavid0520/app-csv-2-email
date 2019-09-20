package com.csvemail.project.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.csvemail.project.model.Email;
import com.csvemail.project.model.User;
import com.csvemail.project.repository.UserRepository;
import com.csvemail.project.utils.EmailService;

@Component
public class UserService {
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public SimpleMailMessage template;
	
	@Autowired
	private PasswordEncoder encoder;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserService() {
	}

	public String prepareEmails() {		
		

		List<User> users = userRepository.findAll();
		// List<String> emails = userRepository.findAll().stream().map(User::getEmail).collect(Collectors.toList());

		users.forEach(user -> {
			
			String email[] = {new StringBuilder().append(user.getName()).append(" <").append(user.getEmail()).append(">").toString()};
			emailService.sendEmail(new Email("EquipoDavid <testdavid2019@gmail.com>", 
						Arrays.stream(email).collect(Collectors.toList()),
						"Bienvenido a Equipo David", 
						new StringBuilder().append("Para ingresar use el siguiente password: ").append(user.getPassword()).toString()));
			user.setPassword(encoder.encode(user.getPassword()));
			userRepository.save(user);
		});

		return "Password emails have been sent";
	}

}
