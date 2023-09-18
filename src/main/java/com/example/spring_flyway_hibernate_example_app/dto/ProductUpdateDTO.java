package com.example.spring_flyway_hibernate_example_app.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Jacksonized
@Builder
public class ProductUpdateDTO {
  public long id;
  public String name;
  public BigDecimal price;
  @Builder.Default
  public Instant updateTime = Instant.now();
}
