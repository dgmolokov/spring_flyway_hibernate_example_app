package com.example.spring_flyway_hibernate_example_app.service;

import com.example.spring_flyway_hibernate_example_app.exception.ObjectNotFoundException;
import com.example.spring_flyway_hibernate_example_app.jpa.Product;
import com.example.spring_flyway_hibernate_example_app.jpa.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;

  public Product findById(Long id) {
    return productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(String.format("Product with ID %d was not found!", id)));
  }

  public List<Product> findAll() {
    return productRepository.findAll();
  }

  public Product saveOrUpdate(Product product) {
    return productRepository.saveOrUpdate(product);
  }

  public void deleteById(Long id) {
    productRepository.deleteById(id);
  }
}
