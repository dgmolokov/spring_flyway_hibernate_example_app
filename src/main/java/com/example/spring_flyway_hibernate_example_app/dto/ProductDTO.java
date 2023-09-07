package com.example.spring_flyway_hibernate_example_app.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
  private long id;
  private String name;
  private BigDecimal price;
  private Instant createdAt;
  private Instant updatedAt;
}
