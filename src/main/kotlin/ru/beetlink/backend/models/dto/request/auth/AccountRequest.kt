package ru.beetlink.backend.models.dto.request.auth

import ru.beetlink.backend.models.entity.user.User
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class AccountInput(
    @field:NotBlank
    @field:Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    val email: String,

    @field:NotBlank
    @field:Size(min = 8)
    val password: String
)

fun AccountInput.toEntity() = User(
    email = email,
    password = password
)