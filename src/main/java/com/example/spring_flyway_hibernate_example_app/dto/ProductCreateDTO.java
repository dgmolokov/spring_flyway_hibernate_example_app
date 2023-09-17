package com.example.spring_flyway_hibernate_example_app.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Jacksonized
@Builder
public class ProductCreateDTO {
  private String name;
  private BigDecimal price;
  private final Instant createTime = Instant.now();
  private final Instant updateTime = Instant.now();
}
