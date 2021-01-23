package com.demo.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {
    @GetMapping(value = "/user")
    public String user(){
        return "user";
    }

    @GetMapping(value = "/admin")
    public String admin(){
        return "admin";
    }
}
