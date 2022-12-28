package com.example.thymeleaf.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/home")
    public String loadHome(Model model) {
        model.addAttribute("name", "Razvan");
        return "home";
    }

    @GetMapping("/elvis")
    public String elvis(Model model) {
        model.addAttribute("isAdmin", false);
        model.addAttribute("gender", "F");
        return "elvis";
    }

    @GetMapping("/each")
    public String loadEach(Model model) {
        List<String> strings = List.of("First", "Second", "Third");
        model.addAttribute("list", strings);
        return "each";
    }
}
