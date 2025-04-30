package com.study.study2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    /**
     * 기본적인 GET
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring boot!";
    }
}
