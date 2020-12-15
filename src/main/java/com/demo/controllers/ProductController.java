package com.demo.controllers;


//import com.demo.engine.product.Producer;

import com.demo.model.Product;
import com.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

//    @Autowired
//    public Producer producer;

    @Autowired
    public ProductService service;

    @PostMapping(value = "/add")
    public void add(@RequestBody Product product) {
        service.add(product);
    }

    @PostMapping(value = "/add/all")
    public void addAll(@RequestBody List<Product> products) { service.addAll(products); }

    @GetMapping(value = "/get")
    public Product get(@RequestParam Long id){
        return service.get(id);
    }

    @GetMapping(value = "/get/all")
    public List<Product> getAll(){
        return service.getAll();
    }

    @GetMapping(value = "/find")
    public List<Product> find(@RequestParam String params) {
        return service.find(params);
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
