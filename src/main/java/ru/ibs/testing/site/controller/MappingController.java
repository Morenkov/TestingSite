package ru.ibs.testing.site.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MappingController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/")
    public String home() {
        return "home";
    }


}
