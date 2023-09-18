package com.example.spring_flyway_hibernate_example_app.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class ApplicationConfiguration {
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
