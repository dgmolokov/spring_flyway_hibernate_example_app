package com.example.spring_flyway_hibernate_example_app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringFlywayHibernateExampleAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringFlywayHibernateExampleAppApplication.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
