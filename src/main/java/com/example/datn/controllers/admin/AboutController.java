package com.example.datn.controllers.admin;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {


    @GetMapping("getabout")
    public String getAbout(Model model) {
        return "user/about";
    }
}
