package com.csvemail.project.batch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import com.csvemail.project.service.UserService;

@Component
public class JobResultListener extends JobExecutionListenerSupport {
	
	private UserService userService;
	
	public JobResultListener(UserService userService) {
		this.userService = userService;
	}
 
	@Override
    public void afterJob(JobExecution jobExecution) {
		
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			userService.prepareEmails();
		}else {
			System.out.println("Envio de Correos Fallido");
		}
    }

}
