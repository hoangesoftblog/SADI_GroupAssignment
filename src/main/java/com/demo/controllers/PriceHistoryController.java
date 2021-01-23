package com.demo.controllers;

import com.demo.model.PriceHistory;
import com.demo.service.PriceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping(value = "/history")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PriceHistoryController {
    @Autowired
    public PriceHistoryService service;

//    @GetMapping(value = "/get")
//    public PriceHistory get(@RequestParam Long id) {
//        return service.get(id);
//    }
}
