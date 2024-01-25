package ru.beetlink.backend.models.dto.request.link

import ru.beetlink.backend.models.entity.link.Link
import ru.beetlink.backend.models.entity.user.User

data class LinkRequest(
    val originalUrl: String,
    val description: String?
)

fun LinkRequest.toEntity(user: User): Link {
    return Link(
        user = user,
        originalUrl = this.originalUrl,
        shortId = null,
        description = description
    )
}

