package com.demo.controllers;

import com.demo.model.login.UserLogin;
import com.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {
    @Autowired
    LoginService service;

    @GetMapping(value = "user")
    public String user(){
        return "user";
    }

    @GetMapping(value = "admin")
    public String admin(){
        return "admin";
    }

    @PostMapping(value = "register")
    public void register(@RequestBody UserLogin registrationInfo) throws LoginService.UserAlreadyExistedException {
        try {
            service.register(registrationInfo);
        } catch (LoginService.UserAlreadyExistedException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    @PostMapping(value = "login")
    public void login(@RequestBody UserLogin loginInfo) {
        try {
            service.register(loginInfo);
        } catch (LoginService.UserAlreadyExistedException exception) {
            exception.printStackTrace();
//            throw exception;
        }
    }
}
