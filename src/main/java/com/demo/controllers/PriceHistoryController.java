package com.demo.controllers;

import com.demo.model.PriceHistory;
import com.demo.service.PriceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/price")
public class PriceHistoryController {
    @Autowired
    public PriceHistoryService service;

//    @PostMapping(value = "/add")
//    public void add(@RequestBody PriceHistory history) { service.add(history); }


}
