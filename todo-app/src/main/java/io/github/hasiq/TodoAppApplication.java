package io.github.hasiq;

import jakarta.validation.Validator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@EnableAsync
@SpringBootApplication
public class TodoAppApplication  {

	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
	}
	@Bean
	Validator validator(){
		return new LocalValidatorFactoryBean();
	}


}
