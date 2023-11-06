package com.happiday.Happi_Day.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("views/login")
    public String login() {
        return "login";
    }

    @GetMapping("views/register")
    public String register() {
        return "register";
    }

    @GetMapping("views/chat")
    public String chat() {
        return "chat";
    }
}
