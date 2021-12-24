package com.example.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }
        @GetMapping("/about")
        public String about(Model model){
            return "about";
    }
}
