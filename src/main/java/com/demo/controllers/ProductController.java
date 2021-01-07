package com.demo.controllers;


//import com.demo.engine.product.Producer;

import com.demo.model.Product;
import com.demo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProductService service;


    @PostMapping(value = "/add")
    public void add(@RequestBody Product product) {
        service.add(product);
    }



    @GetMapping(value = "/get/{id}")
//    @Cacheable(value = "product", key = "#id", unless = "#result == null")
    public Product get(@PathVariable Long id){
        return service.get(id);
    }

//    @GetMapping(value = "/get/all")
//    public List<Product> getAllNotHidden() {
//        return service.getAllNotHidden();
//    }

    @GetMapping(value = "/get/category/{id}")
    public List<Product> findByCategoryId(@PathVariable Long id) { return service.findByCategoryId(id); }

    @GetMapping(value = "/find")
    public List<Product> find(@RequestParam String params) {
        return service.find(params);
    }

    @GetMapping(value = "/get/all")
    public Page<Product> getAllNotHiddenWithPage(@RequestParam(defaultValue = "0") int page) {
        return service.getAllNotHiddenWithPage(page);
    }


    @PutMapping(value = "/update")
    public void update(@RequestBody Product product) {
        product.setHistories(null);
        service.update(product);
    }

    @DeleteMapping(value = "/delete")
    public void delete(@RequestParam Long id){
        service.delete(id);
    }
}
