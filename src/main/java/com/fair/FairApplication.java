package com.fair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.PathSelectors;


import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import org.springframework.context.annotation.Import;

/**
 * The main class of Spring Boot
 */

@SpringBootApplication
@EnableSwagger2
@EnableAsync
@EnableScheduling
@Import(BeanValidatorPluginsConfiguration.class)
public class FairApplication {

	public static void main(String[] args) {
		SpringApplication.run(FairApplication.class, args);
	}

	@Bean
	public Docket productApi() {
	   return new Docket(DocumentationType.SWAGGER_2).select()
		  .apis(RequestHandlerSelectors.basePackage("com.fair.controller"))
		  .paths(PathSelectors.ant("/fair/*"))
		  .build();

	}

}
