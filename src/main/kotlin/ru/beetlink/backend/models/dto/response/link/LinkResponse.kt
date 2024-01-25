package ru.beetlink.backend.models.dto.response.link

import ru.beetlink.backend.models.entity.link.Link

data class LinkInfo(
    val id: Long,
    val originalUrl: String,
    val shortUrl: String,
    val description: String
)

fun Link.toDto(): LinkInfo {
    return LinkInfo(
        id = id,
        originalUrl = originalUrl,
        shortUrl = shortId,
        description = description
    )
}
