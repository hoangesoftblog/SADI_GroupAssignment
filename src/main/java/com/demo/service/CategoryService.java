package com.demo.service;

import com.demo.model.Category;
import com.demo.repository.CategoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class CategoryService implements RemoveDependency<Category> {

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
//    @Cacheable(value = "category", key = "#shopeeCategoryID", unless = "#result == null")
    public Category getCategoryByShopeeID(Long shopeeCategoryID) {
        Optional<Category> c = categoryStore.findByShopeeCategoryID(shopeeCategoryID);
//        c.ifPresent(this::GetMethod_RemoveDependency);
        return c.orElse(null);
    }

//    public Long getIDCategoryByShopeeID(Long shopeeCategoryID) {
//        Category c = getCategoryByShopeeID(shopeeCategoryID);
//        Long returnID = Optional.ofNullable(c).map(Category::getId).orElse(null);
//        return returnID;
//    }

    @Override
    public Category GetMethod_RemoveDependency(Category category) {
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
