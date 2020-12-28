package com.demo.repository;


import com.demo.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductStore extends CrudRepository<Product, Long> {
    @Override
    List<Product> findAll();

    List<Product> findByName(String name);

    List<Product> findByNameOrUrl(String name, String URL);

    @Override
    List<Product> findAllById(Iterable<Long> longs);

    List<Product> findAllByIsHiddenFalse();
}