package com.example.spring_flyway_hibernate_example_app.controller;

import com.example.spring_flyway_hibernate_example_app.dto.ProductCreateDTO;
import com.example.spring_flyway_hibernate_example_app.dto.ProductDTO;
import com.example.spring_flyway_hibernate_example_app.dto.ProductUpdateDTO;
import com.example.spring_flyway_hibernate_example_app.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
  private final ProductService productService;

  @GetMapping("/{id}")
  public ProductDTO findById(@PathVariable long id) {
    return productService.findById(id);
  }

  @GetMapping("/all")
  public List<ProductDTO> findAll() {
    return productService.findAll();
  }

  @PostMapping
  public ProductDTO create(@RequestBody ProductCreateDTO productCreateDTO) {
    return productService.create(productCreateDTO);
  }

  @PutMapping
  public ProductDTO update(@RequestBody ProductUpdateDTO productUpdateDTO) {
    return productService.update(productUpdateDTO);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable long id) {
    productService.delete(id);
  }
}
