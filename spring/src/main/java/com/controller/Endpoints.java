package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Endpoints {

    @RequestMapping("/test")
    public String Test() {
        return "Hello world!";
    }
}
