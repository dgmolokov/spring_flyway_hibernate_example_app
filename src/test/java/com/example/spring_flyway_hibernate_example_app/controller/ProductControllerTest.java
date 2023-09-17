package com.example.spring_flyway_hibernate_example_app.controller;

import com.example.spring_flyway_hibernate_example_app.dto.ProductCreateDTO;
import com.example.spring_flyway_hibernate_example_app.dto.ProductDTO;
import com.example.spring_flyway_hibernate_example_app.dto.ProductUpdateDTO;
import com.example.spring_flyway_hibernate_example_app.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jooq.lambda.Unchecked;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {
  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @MockBean
  ProductService productService;

  @Test
  void findById() {
    var productDTO = ProductDTO.builder()
      .name("product1")
      .price(new BigDecimal(1000))
      .build();
    var productDTOJson = Unchecked.supplier(() -> objectMapper.writeValueAsString(productDTO)).get();
    doReturn(productDTO).when(productService).findById(productDTO.getId());

    Unchecked.supplier(
      () -> mockMvc.perform(get(String.format("/products/%d", productDTO.getId())))
        .andExpect(status().isOk())
        .andExpect(content().json(productDTOJson))
    ).get();
    verify(productService, times(1)).findById(productDTO.getId());
    verifyNoMoreInteractions(productService);
  }

  @Test
  void findAll() {
    var products = new ArrayList<ProductDTO>();
    products.add(ProductDTO.builder()
      .name("product1")
      .price(new BigDecimal(1000))
      .build());
    products.add(ProductDTO.builder()
      .name("product2")
      .price(new BigDecimal(1000))
      .build());
    var productsJson = Unchecked.supplier(() -> objectMapper.writeValueAsString(products)).get();
    doReturn(products).when(productService).findAll();

    Unchecked.supplier(() -> mockMvc.perform(get("/products/all"))
      .andExpect(status().isOk())
      .andExpect(content().json(productsJson))
    ).get();
    verify(productService, times(1)).findAll();
    verifyNoMoreInteractions(productService);
  }

  @Test
  void create() {
    var productCreateDTO = ProductCreateDTO.builder()
      .name("product1")
      .price(new BigDecimal(1000))
      .build();
    var productDTO = ProductDTO.builder()
      .name("product1")
      .price(new BigDecimal(1000))
      .build();
    var productCreateDTOJson = Unchecked.supplier(() -> objectMapper.writeValueAsString(productCreateDTO)).get();
    var productDTOJson = Unchecked.supplier(() -> objectMapper.writeValueAsString(productDTO)).get();
    doReturn(productDTO).when(productService).create(any());

    Unchecked.supplier(() -> mockMvc.perform(post("/products")
        .content(productCreateDTOJson)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().json(productDTOJson))
    ).get();
    verify(productService, times(1)).create(any());
    verifyNoMoreInteractions(productService);
  }

  @Test
  void update() {
    var productUpdateDTO = ProductUpdateDTO.builder()
      .name("product1")
      .price(new BigDecimal(1000))
      .build();
    var productUpdateDTOJson = Unchecked.supplier(() -> objectMapper.writeValueAsString(productUpdateDTO)).get();
    var productDTO = ProductDTO.builder()
      .name("product1")
      .price(new BigDecimal(1000))
      .build();
    var productDTOJson = Unchecked.supplier(() -> objectMapper.writeValueAsString(productDTO)).get();
    doReturn(productDTO).when(productService).update(any());

    Unchecked.supplier(() -> mockMvc.perform(put("/products")
        .content(productUpdateDTOJson)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().json(productDTOJson))
    ).get();
    verify(productService, times(1)).update(any());
    verifyNoMoreInteractions(productService);
  }

  @Test
  void delete() {
    long id = 0L;
    doNothing().when(productService).delete(id);

    Unchecked.supplier(() -> mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/products/%d", id)))
      .andExpect(status().isOk())
    ).get();
    verify(productService, times(1)).delete(id);
    verifyNoMoreInteractions(productService);
  }
}
