package ru.beetlink.backend.controllers

import ru.beetlink.backend.models.dto.request.user.RoleRequest
import ru.beetlink.backend.models.dto.response.user.UserInfo
import ru.beetlink.backend.models.entity.user.Role
import ru.beetlink.backend.services.UserService
import ru.beetlink.backend.utils.exception.AccessDeniedException
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.beetlink.backend.models.dto.response.link.LinkInfo
import java.io.File

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    @Autowired private val userService: UserService
) {
    @GetMapping("/{userId}/links")
    @ResponseBody
    fun getUserLinks(@PathVariable userId: String, auth: Authentication): List<LinkInfo> {
        if (userId == "self") {
            return userService.getUserLinks(auth.principal as Long)
        } else throw AccessDeniedException()
    }

    @PostMapping("/{userId}/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    fun setUserRoles(@PathVariable userId: String, @RequestBody role: RoleRequest, auth: Authentication) {
        val realId = if (userId == "self") auth.principal as Long else userId.toLong()
        if (realId != auth.principal as Long && !(auth.authorities as List<*>).contains(Role.ADMIN)) {
            throw AccessDeniedException()
        }
        userService.setUserRole(realId, role.role)
    }
}