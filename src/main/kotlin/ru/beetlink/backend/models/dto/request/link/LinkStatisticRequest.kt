package ru.beetlink.backend.models.dto.request.link

import ru.beetlink.backend.models.entity.link.Link
import ru.beetlink.backend.models.entity.link.LinkStatistic
import java.time.LocalDateTime

data class LinkStatisticRequest(
    val linkId: Long,
    val ipAddress: String,
    val language: String?,
    val deviceType: String?,
    val referer: String?
)

fun LinkStatisticRequest.toEntity(link: Link): LinkStatistic {
    return LinkStatistic(
        link = link,
        ipAddress = this.ipAddress,
        language = this.language ?: "неизвестно",
        deviceType = this.deviceType ?: "неизвестно",
        referer = this.referer ?: "прямое посещение"
    )
}
