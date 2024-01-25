package ru.beetlink.backend.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import ru.beetlink.backend.models.dto.response.user.UserInfo
import ru.beetlink.backend.services.UserService
import ru.beetlink.backend.utils.exception.NotFoundException

@Controller
class WebController (
    @Autowired private val userService: UserService
) {
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