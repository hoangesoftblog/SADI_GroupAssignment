package com.demo.controllers;

import com.demo.DTO.CategoryDTO;
import com.demo.DTO_Converter.CategoryDTOService;
import com.demo.model.Category;
import com.demo.model.Product;
import com.demo.service.CategoryService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {
    @Autowired
    public CategoryDTOService service;

//    @GetMapping(value = "/get")
//    @Cacheable(value = "category", key = "#shopeeID", unless = "#result == null")
//    public CategoryDTO getByShopeeCategoryID(@RequestParam Long shopeeID) {
//        return service.getCategoryByShopeeID(shopeeID);
//    }

    @GetMapping(value = "/get/{id}")
    @Cacheable(value = "category", key = "#id", unless = "#result == null")
    public CategoryDTO getByID(@PathVariable Long id) {return service.findByID(id);}

    @GetMapping(value = "/get/all")
    @Cacheable(value = "category_all", key = "#root.methodName", unless = "#result == null")
    public List<CategoryDTO> getAllCategories(){
        return service.getAll();
    }

    @GetMapping(value = "/get/all/page/{page}")
    @Cacheable(value = "category_page", key = "#page",  unless = "#result == null")
    public Page<CategoryDTO> getAllCategories(int page){
        return service.getAll(page);
    }

    @GetMapping(value = "/get")
    public List<CategoryDTO> getCategoriesByProductIDs(@RequestParam(required = false) List<Long> product_ids) {
        if(product_ids == null || product_ids.size() == 0){
            return getAllCategories();
        } else {
            return service.findByProductIDs(product_ids);
        }
    }
}
