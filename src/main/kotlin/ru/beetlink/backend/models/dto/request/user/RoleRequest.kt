package ru.beetlink.backend.models.dto.request.user

import com.fasterxml.jackson.annotation.JsonInclude
import ru.beetlink.backend.models.entity.user.Role
import jakarta.validation.constraints.NotBlank

data class RoleRequest(
    @field:NotBlank
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    val role: Role
)