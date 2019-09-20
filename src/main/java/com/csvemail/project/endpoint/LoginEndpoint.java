package com.csvemail.project.endpoint;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csvemail.project.model.CredentialsPojo;
import com.csvemail.project.model.CredentialsUpdatePojo;
import com.csvemail.project.model.User;
import com.csvemail.project.repository.UserRepository;
import com.csvemail.project.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginEndpoint {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping
	public ResponseEntity<String> loadUsers(@RequestBody CredentialsPojo credentials){
		try {
			User user =userRepository.getByUserName(credentials.getUsername());
			if(Objects.nonNull(user)) {
				return ResponseEntity.status(HttpStatus.OK).body(loginService.validateUserCredentials(credentials, user));
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The username does not exist");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping(value="updateCredentials")
	public ResponseEntity<String> updateCredentials(@RequestBody CredentialsUpdatePojo credentials){
		try {
			User user =userRepository.getByUserName(credentials.getUsername());
			if(Objects.nonNull(user)) {
				return ResponseEntity.status(HttpStatus.OK).body(loginService.updateUserCredentials(credentials, user));
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The username does not exist");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
