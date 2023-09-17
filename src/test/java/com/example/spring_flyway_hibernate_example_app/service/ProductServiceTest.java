package com.example.spring_flyway_hibernate_example_app.service;

import com.example.spring_flyway_hibernate_example_app.annotation.TransactionalIntegrationTestContainers;
import com.example.spring_flyway_hibernate_example_app.dto.ProductCreateDTO;
import com.example.spring_flyway_hibernate_example_app.dto.ProductDTO;
import com.example.spring_flyway_hibernate_example_app.dto.ProductUpdateDTO;
import com.example.spring_flyway_hibernate_example_app.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

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
    var resultDTO = productService.create(creationDTO);
    var expectedDTO = ProductDTO.builder()
      .id(resultDTO.getId())
      .name(creationDTO.getName())
      .price(creationDTO.getPrice())
      .createTime(creationDTO.getCreateTime())
      .updateTime(creationDTO.getUpdateTime())
      .build();

    assertEquals(expectedDTO, resultDTO);
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
    var expectedDTO = ProductDTO.builder()
      .id(createdProductDTO.getId())
      .name(updateDTO.getName())
      .price(updateDTO.getPrice())
      .createTime(createdProductDTO.getCreateTime())
      .updateTime(updateDTO.getUpdateTime())
      .build();
    var resultProductDTO = productService.update(updateDTO);

    assertAll(
      () -> assertEquals(expectedDTO, resultProductDTO),
      () -> assertTrue(resultProductDTO.updateTime.isAfter(resultProductDTO.createTime))
    );
  }

  @Test
  void update_nonExistent_throwsObjectNotFoundException() {
    var updateDTO = ProductUpdateDTO.builder()
      .id(12345)
      .name("product2")
      .price(new BigDecimal(2000))
      .build();
    assertThrows(ObjectNotFoundException.class, () -> productService.update(updateDTO));
  }

  @Test
  void findById() {
    var creationDTO = ProductCreateDTO.builder()
      .name("product1")
      .price(new BigDecimal(1000))
      .build();
    var createdProductDTO = productService.create(creationDTO);
    var expectedDTO = ProductDTO.builder()
      .id(createdProductDTO.getId())
      .name(creationDTO.getName())
      .price(creationDTO.getPrice())
      .createTime(creationDTO.getCreateTime())
      .updateTime(creationDTO.getUpdateTime())
      .build();

    assertEquals(expectedDTO, productService.findById(createdProductDTO.getId()));
  }

  @Test
  void findById_nonExistent_throwsObjectNotFoundException() {
    assertThrows(ObjectNotFoundException.class, () -> productService.findById(12345));
  }

  @Test
  void findAll() {
    var expectedList = List.of(
      productService.create(ProductCreateDTO.builder()
        .name("product1")
        .price(new BigDecimal(1000))
        .build()),
      productService.create(ProductCreateDTO.builder()
        .name("product2")
        .price(new BigDecimal(2000))
        .build())
    );

    assertEquals(expectedList, productService.findAll());
  }

  @Test
  void delete() {
    var createdProductDTO1 = productService.create(ProductCreateDTO.builder()
      .name("product1")
      .price(new BigDecimal(1000))
      .build()
    );
    var createdProductDTO2 = productService.create(ProductCreateDTO.builder()
      .name("product1")
      .price(new BigDecimal(1000))
      .build()
    );
    productService.delete(createdProductDTO1.id);

    assertAll(
      () -> assertEquals(productService.findAll().size(), 1),
      () -> assertTrue(productService.findAll().contains(createdProductDTO2))
    );
  }

  @Test
  void delete_nonExistent_throwsObjectNotFoundException() {
    assertThrows(ObjectNotFoundException.class, () -> productService.delete(12345));
  }
}
