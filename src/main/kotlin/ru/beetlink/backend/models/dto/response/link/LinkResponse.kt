package ru.beetlink.backend.models.dto.response.link

import ru.beetlink.backend.models.entity.link.Link

data class LinkInfo(
    val id: Long,
    val iosUrl: String?,
    val androidUrl: String?,
    val desktopUrl: String?,
    val defaultUrl: String,
    val shortUrl: String,
    val description: String?
)

fun Link.toDto(): LinkInfo {
    return LinkInfo(
        id = id,
        iosUrl = iosUrl,
        androidUrl = androidUrl,
        desktopUrl = desktopUrl,
        defaultUrl = defaultUrl,
        shortUrl = shortId ?: "error",
        description = description
    )
}
