package com.demo.repository;


import com.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductStore extends JpaRepository<Product, Long> {
    @Override
    List<Product> findAll();

    List<Product> findByName(String name);

    List<Product> findByNameOrUrl(String name, String URL);

    @Override
    List<Product> findAllById(Iterable<Long> longs);

    List<Product> findAllByIsHiddenFalse();

    Optional<Product> findFirstByShopeeIDAndShopeeShopID(Long shopeeID, Long shopeeShopID);

    List<Product> findAllByCategories_ShopeeCategoryID(Long categoryID);

    Page<Product> findAllByHiddenIsFalse(Pageable pageable);

    Page<Product> findAll(Pageable pageable);
}