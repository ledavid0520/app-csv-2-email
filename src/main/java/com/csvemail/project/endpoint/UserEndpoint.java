package com.csvemail.project.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserEndpoint {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private  Job job;
	
	@GetMapping(value="/load")
	public ResponseEntity<BatchStatus> loadUsers(){
		try {
			Map<String, JobParameter> parameterMap = new HashMap<>();
			parameterMap.put("time", new JobParameter(System.currentTimeMillis()));
			JobParameters jobParameters = new JobParameters(parameterMap);
			JobExecution jobExecution = jobLauncher.run(job, jobParameters);
			
			System.out.println("Batch is running...");
			while(jobExecution.isRunning()) {
				System.out.println("...");
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(jobExecution.getStatus());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}
