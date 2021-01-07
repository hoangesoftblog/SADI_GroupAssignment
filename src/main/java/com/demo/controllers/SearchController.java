package com.demo.controllers;

import com.demo.model.Product;
import com.demo.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/search")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SearchController {
    @Autowired
    public SearchService searchService;

    @GetMapping
    public List<Product> search(@RequestParam String param) {
        return searchService.search(param);
    }
}
