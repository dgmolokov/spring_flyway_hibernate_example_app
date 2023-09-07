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
public class ProductCreateDTO {
  private String name;
  private BigDecimal price;
  private final Instant createdAt = Instant.now();
  private final Instant updatedAt = Instant.now();
}
