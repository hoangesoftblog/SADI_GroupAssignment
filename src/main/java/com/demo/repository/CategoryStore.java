package com.demo.repository;

import com.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryStore extends JpaRepository<Category, Long> {
    Optional<Category> findFirstByShopeeCategoryID(Long shopeeCategoryID);

    @Override
    List<Category> findAll();
}
