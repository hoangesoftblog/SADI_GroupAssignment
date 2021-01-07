package com.demo.service;

import com.demo.model.Category;
import com.demo.model.Product;
import com.demo.repository.CategoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class CategoryService implements RemoveDependency<Category> {

    @PersistenceContext
    public EntityManager entityManager;

//    @Autowired
//    public CategoryService(EntityManagerFactory entityManagerFactory) {
//        this.entityManager = entityManagerFactory.createEntityManager();
//    }

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CategoryStore categoryStore;

    /**
     * Warning: Return value maybe null, we should use Optional to prevent NullPointerException.
     * Can not return Optional because we need it to cache
     * @param shopeeCategoryID
     * @return Category
     */
    public Category getCategoryByShopeeID(Long shopeeCategoryID) {
        Optional<Category> c = categoryStore.findFirstByShopeeCategoryID(shopeeCategoryID);
        c.ifPresent(this::GetMethod_RemoveDependency);
        return c.orElse(null);
    }

    @Override
    public Category GetMethod_RemoveDependency(Category category) {

        Set<Product> products = category.getProducts()
                .stream()
                .map(Product::clone)
                .collect(Collectors.toSet());
        category.setProducts(products);

        return category;
    }

    public List<Category> getAll(){
        List<Category> categories = categoryStore.findAll();
        categories.forEach(this::GetMethod_RemoveDependency);
        return categories;
    }

    public Category findByID(Long id){
        Optional<Category> c = categoryStore.findById(id);
        c.ifPresent(this::GetMethod_RemoveDependency);
        return c.orElse(null);
    }
}
