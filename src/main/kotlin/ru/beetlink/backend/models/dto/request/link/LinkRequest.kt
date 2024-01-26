package ru.beetlink.backend.models.dto.request.link

import ru.beetlink.backend.models.entity.link.Link
import ru.beetlink.backend.models.entity.user.User

data class LinkRequest(
    val iosUrl: String?,
    val androidUrl: String?,
    val desktopUrl: String?,
    val defaultUrl: String,
    val description: String?
)

fun LinkRequest.toEntity(user: User): Link {
    return Link(
        user = user,
        iosUrl = iosUrl,
        androidUrl = androidUrl,
        desktopUrl = desktopUrl,
        defaultUrl = defaultUrl,
        shortId = null,
        description = description
    )
}

