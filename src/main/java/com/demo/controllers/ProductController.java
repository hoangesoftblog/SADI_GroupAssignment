package com.demo.controllers;


import com.demo.DTO.ProductDTO;
import com.demo.DTO.ProductWithMoreLikeThisDTO;
import com.demo.DTO_Converter.ProductDTOService;
import com.demo.DTO_Converter.ProductWithMoreLikeThisDTOService;
import com.demo.model.Product;
import com.demo.model.RandomInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    public RandomInterface service;

    @PostMapping(value = "/add")
    public void add(@RequestBody Product product) {
        service.add(product);
    }




    @Autowired
    public ProductWithMoreLikeThisDTOService productWithMoreLikeThisDTOService;

    @GetMapping(value = "/get/{id}")
    @Cacheable(value = "product", key = "#id", unless = "#result == null")
    public ProductWithMoreLikeThisDTO get(@PathVariable Long id){
        return productWithMoreLikeThisDTOService.get(id);
    }

    @Autowired
    public ProductDTOService productDTOService;

    @GetMapping(value = "/get/category/{id}")
    @Cacheable(value = "product_category", key = "#id-#page", unless = "#result == null")
    public Page<ProductDTO> findByCategoryId(@PathVariable Long id, @RequestParam(defaultValue = "0") int page) { return productDTOService.findByCategoryId(id, page); }

//    @GetMapping(value = "/find")
//    public List<ProductDTO> find(@RequestParam String params) {
//        return productDTOService.find(params);
//    }

    @GetMapping(value = "/get/all/page/{page}")
    @Cacheable(value = "product_all", key = "#page", unless = "#result == null")
    public Page<ProductDTO> getAllNotHiddenWithPage(@PathVariable int page) {
        return productDTOService.getAllWithPage(page);
    }

    @GetMapping(value = "/get/all/if_you_get_this_api_this_will_be_the_end_of_the_world")
    public List<ProductDTO> getAll() {
        return productDTOService.getAll();
    }




//    @PutMapping(value = "/update")
//    public void update(@RequestBody Product product) {
//        product.setHistories(null);
//        service.update(product);
//    }
//
//    @DeleteMapping(value = "/delete")
//    public void delete(@RequestParam Long id){
//        service.delete(id);
//    }
}
