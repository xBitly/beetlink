package ru.beetlink.backend.controllers

import ru.beetlink.backend.models.dto.request.auth.AccountInput
import ru.beetlink.backend.models.dto.request.auth.CredentialsRequest
import ru.beetlink.backend.models.dto.request.auth.PasswordRequest
import ru.beetlink.backend.models.dto.request.auth.RefreshTokenRequest
import ru.beetlink.backend.models.dto.response.auth.AuthTokenInfo
import ru.beetlink.backend.services.AuthService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("api/v1/auth")
class AuthController(
    @Autowired private val authService: AuthService
) {
    @PostMapping("/signup")
    @ResponseBody
    fun signup(@Valid @RequestBody request: AccountInput): AuthTokenInfo {
        return authService.signup(request)
    }

    @PostMapping("/signin")
    @ResponseBody
    fun signin(@Valid @RequestBody request: CredentialsRequest): AuthTokenInfo {
        return authService.signin(request)
    }

    @PostMapping("/refresh")
    @ResponseBody
    fun refresh(@Valid @RequestBody request: RefreshTokenRequest): AuthTokenInfo {
        return authService.refresh(request.refreshToken)
    }

    @PostMapping("/signout")
    @ResponseBody
    fun signout(auth: Authentication) {
        authService.signout(auth.principal as Long)
    }

    @PostMapping("/password")
    @ResponseBody
    fun setPassword(@Valid @RequestBody request: PasswordRequest, auth: Authentication) {
        authService.setPassword(auth.principal as Long, request)
    }
}