package com.demo.controllers;


//import com.demo.engine.product.Producer;

import com.demo.model.Product;
import com.demo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

//    @Autowired
//    public Producer producer;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProductService service;

    @PostMapping(value = "/add")
    public void add(@RequestBody Product product) {
        service.add(product);
    }

    @PostMapping(value = "/add/all")
    public void addAll(@RequestBody List<Product> products) { service.addAll(products); }

    @GetMapping(value = "/get")
    @Cacheable(value = "product", key = "#id")
    public Product get(@RequestParam Long id){
        logger.info("Get product with ID = " + id);
        return service.get(id);
    }

//    @GetMapping(value = "/get/all")
//    @Cacheable(value = "products", key = "#")
//    public List<Product> getAll(){
//        return service.getAll();
//    }

    @GetMapping(value = "/find")
    public List<Product> find(@RequestParam String params) {
        return service.find(params);
    }

    @GetMapping(value = "/get/all")
    public List<Product> getAllNotHidden() {
        return service.findAllNotHidden();
    }


    @PutMapping(value = "/update")
    @CacheEvict(value = "product", key = "#product.id")
    public void update(@RequestBody Product product) {
        logger.info("Update product with ID = " + product.getId());

        product.setHistories(null);
        service.update(product);
    }

    @DeleteMapping(value = "/delete")
    public void delete(@RequestParam Long id){
        service.delete(id);
    }
}
