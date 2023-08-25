package com.example.spring_flyway_hibernate_example_app.dto;

import lombok.*;

import java.math.BigDecimal;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
  private Long id;
  private String name;
  private BigDecimal price;
}
