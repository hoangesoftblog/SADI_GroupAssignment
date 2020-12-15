package com.demo.repository;


import com.demo.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductStore extends CrudRepository<Product, Long> {
    @Override
    List<Product> findAll();

    List<Product> findByName(String name);

    List<Product> findByNameOrUrl(String name, String URL);
}