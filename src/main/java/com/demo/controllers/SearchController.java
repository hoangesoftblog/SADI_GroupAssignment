package com.demo.controllers;

import com.demo.DTO.ProductDTO;
import com.demo.DTO_Converter.ProductDTOService;
import com.demo.model.Product;
import com.demo.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/search")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SearchController {
    private final String MAX_VALUE = String.valueOf(Long.MAX_VALUE);

    @Autowired
    public ProductDTOService searchService;

    @GetMapping
    public List<ProductDTO> search(@RequestParam String keywords,
                                   @RequestParam(required = false) Long price_higher_than, @RequestParam(required = false) Long price_lower_than,
                                   @RequestParam(required = false) Double rating_higher_than, @RequestParam(required = false) Double rating_lower_than,
                                   @RequestParam(required = false) List<String> brands,
                                   @RequestParam(required = false) List<String> categories ,
                                   @RequestParam(defaultValue = "relevance") String by, @RequestParam(required = false) String order) {
        if (order == null) {
            if (by.equals("relevance")) order = "desc";
            else order = "asc";
        }
        System.out.println(keywords + " " + price_higher_than + " " +  price_lower_than + " " +  rating_higher_than + " " +  rating_lower_than);

        return searchService.search(keywords, price_higher_than, price_lower_than, rating_higher_than, rating_lower_than, brands, categories, by, order);
    }
}
