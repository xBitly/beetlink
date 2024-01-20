package ru.beetlink.backend.models.entity.user

import org.springframework.security.core.GrantedAuthority

enum class Role(private val value: String) : GrantedAuthority {
    ADMIN("ADMIN"),
    PREMIUM_USER("PREMIUM_USER"),
    USER("USER"),
    UNCONFIRMED_USER("UNCONFIRMED_USER");

    override fun getAuthority() = value
}