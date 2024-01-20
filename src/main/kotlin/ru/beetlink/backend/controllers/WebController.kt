package ru.beetlink.backend.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebController {
    @GetMapping(value = ["", "/"])
    fun showLanding(): String {
        return "index"
    }

    @GetMapping("/signin")
    fun signin(): String {
        return "signin"
    }

    @GetMapping("/signup")
    fun signup(): String {
        return "signup"
    }
}