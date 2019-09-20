package com.csvemail.project.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.csvemail.project.model.User;
import com.csvemail.project.repository.UserRepository;

@Component
public class DataBaseWriter implements ItemWriter<User>{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void write(List<? extends User> users) throws Exception {	
		try {
			userRepository.saveAll(users);		
		}  catch (DataIntegrityViolationException e) {
	        System.out.println("The email has to be unique");
	    } catch (Exception e) {
			// TODO: handle exception
		}
	}

}
