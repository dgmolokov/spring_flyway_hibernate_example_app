package com.example.spring_flyway_hibernate_example_app.service;

import com.example.spring_flyway_hibernate_example_app.dto.ProductCreateDTO;
import com.example.spring_flyway_hibernate_example_app.dto.ProductDTO;
import com.example.spring_flyway_hibernate_example_app.dto.ProductUpdateDTO;
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
  private final ModelMapper modelMapper;

  public ProductDTO findById(long id) {
    var product = productRepository.findById(id).orElseThrow(() ->
      new ObjectNotFoundException(String.format("Product with ID %d was not found!", id)));
    return modelMapper.map(product, ProductDTO.class);
  }

  public List<ProductDTO> findAll() {
    return productRepository.findAll().stream()
      .map(product -> modelMapper.map(product, ProductDTO.class))
      .collect(Collectors.toList());
  }

  public ProductDTO create(ProductCreateDTO productCreateDTO) {
    var product = modelMapper.map(productCreateDTO, Product.class);
    return modelMapper.map(productRepository.save(product), ProductDTO.class);
  }

  public ProductDTO update(ProductUpdateDTO productUpdateDTO) {
    var id = productUpdateDTO.getId();
    var product = productRepository.findById(id).orElseThrow(() ->
      new ObjectNotFoundException(String.format("Product with ID %d was not found!", id)));
    modelMapper.map(productUpdateDTO, product);
    return modelMapper.map(productRepository.save(product), ProductDTO.class);
  }

  public void delete(long id) {
    if (productRepository.existsById(id)) {
      productRepository.deleteById(id);
    } else {
      throw new ObjectNotFoundException(String.format("Product with ID %d was not found!", id));
    }
  }
}