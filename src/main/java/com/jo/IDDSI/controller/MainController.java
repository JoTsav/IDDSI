package com.jo.IDDSI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // This controller handles the main pages of the application
    @GetMapping({"/", "/home"})
    public String home() {
        return "home"; // This should correspond to home.html in templates
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
} 