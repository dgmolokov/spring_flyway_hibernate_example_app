package com.example.spring_flyway_hibernate_example_app.service;

import com.example.spring_flyway_hibernate_example_app.exception.ObjectNotFoundException;
import com.example.spring_flyway_hibernate_example_app.jpa.Product;
import com.example.spring_flyway_hibernate_example_app.jpa.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {
  @Autowired
  ProductService productService;
  @MockBean
  ProductRepository productRepository;

  @Test
  void saveOrUpdate() {
    var product = Product.builder().name("product1").price(new BigDecimal(10000)).build();
    doReturn(product).when(productRepository).saveOrUpdate(product);

    assertEquals(product, productService.saveOrUpdate(product));
    verify(productRepository, times(1)).saveOrUpdate(any());
    verifyNoMoreInteractions(productRepository);
  }

  @Test
  void findById() {
    var product = Product.builder().id(1L).name("product1").price(new BigDecimal(10000)).build();
    doReturn(Optional.of(product)).when(productRepository).findById(product.getId());

    assertEquals(product, productService.findById(product.getId()));
    verify(productRepository, times(1)).findById(any());
    verifyNoMoreInteractions(productRepository);
  }

  @Test
  void findByIdThrowsException() {
    doReturn(Optional.empty()).when(productRepository).findById(any());

    assertThrows(ObjectNotFoundException.class, () -> productService.findById(1L));
    verify(productRepository, times(1)).findById(any());
    verifyNoMoreInteractions(productRepository);
  }

  @Test
  void findAll() {
    var products = List.of(
      Product.builder().name("product1").price(new BigDecimal(10000)).build(),
      Product.builder().name("product2").price(new BigDecimal(10001)).build()
    );
    doReturn(products).when(productRepository).findAll();

    assertEquals(products, productService.findAll());
    verify(productRepository, times(1)).findAll();
    verifyNoMoreInteractions(productRepository);
  }

  @Test
  void deleteById() {
    doNothing().when(productRepository).deleteById(any());

    productService.deleteById(1L);
    verify(productRepository, times(1)).deleteById(any());
    verifyNoMoreInteractions(productRepository);
  }
}
