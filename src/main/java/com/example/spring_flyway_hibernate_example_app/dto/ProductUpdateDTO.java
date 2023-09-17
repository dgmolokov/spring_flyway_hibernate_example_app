package com.example.spring_flyway_hibernate_example_app.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Jacksonized
@Builder
public class ProductUpdateDTO {
  public final long id;
  public final String name;
  public final BigDecimal price;
  public final Instant updateTime = Instant.now();
}
