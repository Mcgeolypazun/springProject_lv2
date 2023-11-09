package com.sparta.springauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")//메인페이지 가기위함
    public String home(Model model) {
        model.addAttribute("username", "username");
        return "index";
    }
}