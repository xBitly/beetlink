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
    fun showSignIn(): String {
        return "signin"
    }

    @GetMapping("/signup")
    fun showSignUp(): String {
        return "signup"
    }

    @GetMapping("/home")
    fun showHome(): String {
        return "home"
    }

    @GetMapping("/terms")
    fun showTerms(): String {
        return "terms"
    }

    @GetMapping("/privacy")
    fun showPrivacy(): String {
        return "privacy"
    }

    @GetMapping("/error")
    fun showError(): String {
        return "error"
    }
}