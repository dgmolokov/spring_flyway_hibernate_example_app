package com.example.spring_flyway_hibernate_example_app.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Jacksonized
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
  public long id;
  public String name;
  public BigDecimal price;
  public Instant createTime;
  public Instant updateTime;
}
