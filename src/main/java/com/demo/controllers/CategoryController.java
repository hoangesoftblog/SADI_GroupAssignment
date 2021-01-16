package com.demo.controllers;

import com.demo.DTO.CategoryDTO;
import com.demo.DTO_Converter.CategoryDTOService;
import com.demo.model.Category;
import com.demo.model.Product;
import com.demo.service.CategoryService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {
    @Autowired
    public CategoryDTOService service;

    @GetMapping(value = "/get")
    public CategoryDTO getByShopeeCategoryID(@RequestParam Long shopeeID) {
        return service.getCategoryByShopeeID(shopeeID);
    }

    @GetMapping(value = "/get/{id}")
    public CategoryDTO getByID(@PathVariable Long id) {return service.findByID(id);}

    @GetMapping(value = "/get/all")
    public List<CategoryDTO> getAllCategories(){
        return service.getAll();
    }

}
