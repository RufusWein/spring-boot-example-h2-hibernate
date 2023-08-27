package com.example.springboot;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.springboot.config.AuthorizationServerConfig;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
	"com.example.springboot.prices.*",
	"com.example.springboot.auth.*"
})
@ComponentScan(basePackages = { 
	"com.example.springboot.config.*",
	"com.example.springboot.auth.*",
	"com.example.springboot.prices.*"		
})
@EntityScan(basePackages = {
	"com.example.springboot.prices.*",
	"com.example.springboot.auth.*"
})
@Import(value = {
	AuthorizationServerConfig.class
})
public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
//	@Value("${key.location}")
//	RSAPublicKey key;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
    
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}

}
