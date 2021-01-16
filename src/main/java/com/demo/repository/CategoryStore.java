package com.demo.repository;

import com.demo.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

import static org.hibernate.jpa.QueryHints.HINT_CACHEABLE;

@Repository
public interface CategoryStore extends JpaRepository<Category, Long> {
    @QueryHints(value = {@QueryHint(name = HINT_CACHEABLE, value = "true")})
    Optional<Category> findByShopeeCategoryID(Long shopeeCategoryID);

    @Override
    List<Category> findAll();

    Page<Category> findAll(Pageable pageable);

}
