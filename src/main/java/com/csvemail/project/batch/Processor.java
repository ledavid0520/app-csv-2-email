package com.csvemail.project.batch;

import java.util.Random;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.csvemail.project.model.User;

@Component
public class Processor implements ItemProcessor<User, User> {
	
	public Processor() {
		// Processor Constructor
	}

	@Override
	public User process(User user) throws Exception {

		String password = new Random()
				.ints(12, 33, 122)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
		
		user.setPassword(password);
		return user;
	}

}
