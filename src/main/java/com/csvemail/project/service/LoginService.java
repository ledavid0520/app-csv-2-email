package com.csvemail.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.csvemail.project.model.CredentialsPojo;
import com.csvemail.project.model.CredentialsUpdatePojo;
import com.csvemail.project.model.User;
import com.csvemail.project.repository.UserRepository;

@Component
public class LoginService {
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;

	private boolean validateAppUserCredentials(String appPassword, String password) {
		return encoder.matches(password, appPassword);
	}
		
	public String validateUserCredentials(CredentialsPojo credentials, User user) {				
		if(validateAppUserCredentials(user.getPassword(), credentials.getPassword())) {
			 return user.getFirstEntry() ? "Please update your password in the service /updateCredentials. Then login again" : "Login successful";
		}
		return "The password is incorrect. Please try again.";
	}

	public String updateUserCredentials(CredentialsUpdatePojo credentials, User user) {
		if(validateAppUserCredentials(user.getPassword(), credentials.getPassword())) {
			user.setPassword(encoder.encode(credentials.getNewPassword()));
			user.setFirstEntry(false);
			userRepository.save(user);
			return "Your password has been changed successfully. Now, you can login again";
		}
		return "The password is incorrect. Please try again.";
	}

}
