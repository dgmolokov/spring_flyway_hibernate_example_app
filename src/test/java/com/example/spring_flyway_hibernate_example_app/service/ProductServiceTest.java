package com.example.spring_flyway_hibernate_example_app.service;

import com.example.spring_flyway_hibernate_example_app.annotation.TransactionalIntegrationTestContainers;
import com.example.spring_flyway_hibernate_example_app.dto.ProductCreateDTO;
import com.example.spring_flyway_hibernate_example_app.dto.ProductDTO;
import com.example.spring_flyway_hibernate_example_app.dto.ProductUpdateDTO;
import com.example.spring_flyway_hibernate_example_app.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@TransactionalIntegrationTestContainers
public class ProductServiceTest {
  @Autowired
  ProductService productService;

  @Test
  void create() {
    var creationDTO = ProductCreateDTO.builder()
      .name("product1")
      .price(new BigDecimal(1000))
      .build();
    var resultProductDTO = productService.create(creationDTO);

    assertThat(creationDTO).usingRecursiveComparison().isEqualTo(resultProductDTO);
  }

  @Test
  void update() {
    var createdProductDTO = productService.create(ProductCreateDTO.builder()
      .name("product1")
      .price(new BigDecimal(1000))
      .build()
    );
    var updateDTO = ProductUpdateDTO.builder()
      .id(createdProductDTO.getId())
      .name("product2")
      .price(new BigDecimal(2000))
      .build();
    var resultProductDTO = productService.update(updateDTO);

    assertAll(
      () -> assertThat(updateDTO).usingRecursiveComparison().isEqualTo(resultProductDTO),
      () -> assertEquals(createdProductDTO.getCreatedAt(), resultProductDTO.getCreatedAt()),
      () -> assertTrue(resultProductDTO.getUpdatedAt().isAfter(createdProductDTO.getCreatedAt()))
    );
  }

  @Test
  void findById() {
    var creationDTO = ProductCreateDTO.builder()
      .name("product1")
      .price(new BigDecimal(1000))
      .build();
    var createdProductDTO = productService.create(creationDTO);

    assertThat(creationDTO).usingRecursiveComparison().isEqualTo(productService.findById(createdProductDTO.getId()));
  }

  @Test
  void findAll() {
    var products = new ArrayList<ProductDTO>();
    products.add(productService.create(ProductCreateDTO.builder()
      .name("product1")
      .price(new BigDecimal(1000))
      .build()
    ));
    products.add(productService.create(ProductCreateDTO.builder()
      .name("product2")
      .price(new BigDecimal(2000))
      .build()
    ));

    assertEquals(products, productService.findAll());
  }

  @Test
  void delete() {
    var createdProductDTO = productService.create(ProductCreateDTO.builder()
      .name("product1")
      .price(new BigDecimal(1000))
      .build()
    );
    var productsSizeBeforeDeletions = productService.findAll().size();
    productService.delete(createdProductDTO.getId());

    assertAll(
      () -> assertThrows(ObjectNotFoundException.class, () -> productService.findById(createdProductDTO.getId())),
      () -> assertEquals(productService.findAll().size(), productsSizeBeforeDeletions - 1)
    );
  }
}
