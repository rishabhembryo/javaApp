package com.examplejenkins.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
    @GetMapping("/say")
    public String say(){
        return "Hello Jenkins";
    }
    @GetMapping("deploy")
    public String deploy() {
        return "Deployed Successfully";
    }
    @GetMapping("webhook")
    public String webhook() {
        return "webhook added Successfully";
    }
}
