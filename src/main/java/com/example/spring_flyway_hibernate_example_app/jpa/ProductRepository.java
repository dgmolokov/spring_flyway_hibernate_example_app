package com.example.spring_flyway_hibernate_example_app.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
  @PersistenceContext
  private EntityManager entityManager;

  public Optional<Product> findById(Long id) {
    return Optional.ofNullable(entityManager.
      createQuery("select p from Product p where p.id = :id", Product.class)
      .setParameter("id", id)
      .getSingleResult());
  }

  public List<Product> findAll() {
    return entityManager.createQuery("select p from Product p", Product.class).getResultList();
  }

  @Transactional
  public Product saveOrUpdate(Product product) {
    return entityManager.merge(product);
  }

  @Transactional
  public void deleteById(Long id) {
    var product = entityManager.find(Product.class, id);
    entityManager.remove(product);
  }
}
