package com.example.spring_flyway_hibernate_example_app.service;

import com.example.spring_flyway_hibernate_example_app.dto.ProductDto;
import com.example.spring_flyway_hibernate_example_app.exception.ObjectNotFoundException;
import com.example.spring_flyway_hibernate_example_app.jpa.Product;
import com.example.spring_flyway_hibernate_example_app.jpa.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private ModelMapper modelMapper;

  public ProductDto findById(Long id) {
    var product = productRepository.findById(id).orElseThrow(() ->
      new ObjectNotFoundException(String.format("Product with ID %d was not found!", id)));
    return modelMapper.map(product, ProductDto.class);
  }

  public List<ProductDto> findAll() {
    var products = productRepository.findAll();
    return products.stream()
      .map(product -> modelMapper.map(product, ProductDto.class))
      .collect(Collectors.toList());
  }

  public ProductDto create(ProductDto productDto) {
    var product = modelMapper.map(productDto, Product.class);
    return modelMapper.map(productRepository.save(product), ProductDto.class);
  }

  public ProductDto update(ProductDto productDto) {
    var id = productDto.getId();
    var product = productRepository.findById(id).orElseThrow(() ->
      new ObjectNotFoundException(String.format("Product with ID %d was not found!", id)));
    product.setName(productDto.getName());
    product.setPrice(productDto.getPrice());
    return modelMapper.map(productRepository.save(product), ProductDto.class);
  }

  public void delete(Long id) {
    productRepository.deleteById(id);
  }
}