package ru.beetlink.backend.models.dto.response.user

import ru.beetlink.backend.models.entity.user.Role
import ru.beetlink.backend.models.entity.user.User

data class UserInfo(
    val id: Long,
    val email: String,
    val role: Role,
    val self: Boolean
)

fun User.toDto(
    self: Boolean
) = UserInfo(
    id = id,
    email = email,
    role = role,
    self = self
)