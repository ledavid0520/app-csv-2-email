package com.csvemail.project.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.csvemail.project.batch.JobResultListener;
import com.csvemail.project.model.User;
import com.csvemail.project.service.UserService;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	Resource csvResource = new ClassPathResource("users.csv");
	
	@Bean
    public UserService getUserService() {
        return new UserService();
    }
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
						ItemReader<User> itemReader, ItemProcessor<User, User> itemProcessor,
						ItemWriter<User> itemWriter) {
		
		Step step = stepBuilderFactory.get("File-Load")
						.<User, User>chunk(100)
						.reader(itemReader)
						.processor(itemProcessor)
						.writer(itemWriter)
						.build();
		
		
		return jobBuilderFactory.get("Load-CSV")
					.incrementer(new RunIdIncrementer())
					.start(step)
					.listener(listener())
					.build();
	}
	
	@Bean
	public FlatFileItemReader<User> itemReader(){
		
		FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(csvResource); 
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}

	@Bean
	public LineMapper<User> lineMapper() {
		
		DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] {"name", "lastname", "email"});
		
		BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(User.class);
		
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		
		return defaultLineMapper;
	}
	
	public JobResultListener listener() {
		return new JobResultListener(getUserService());
	}

}
