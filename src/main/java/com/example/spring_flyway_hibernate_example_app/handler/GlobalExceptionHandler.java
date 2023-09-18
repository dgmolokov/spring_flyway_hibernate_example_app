package com.example.spring_flyway_hibernate_example_app.handler;

import com.example.spring_flyway_hibernate_example_app.exception.ObjectNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

  @ExceptionHandler(ObjectNotFoundException.class)
  public void handleObjectNotFountException(Exception exception) {
    logger.warning(exception.getMessage());
  }
}
