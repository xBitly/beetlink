package ru.beetlink.backend.models.dto.request.link

import ru.beetlink.backend.models.entity.link.Link
import ru.beetlink.backend.models.entity.link.LinkStatistic
import java.time.LocalDateTime

data class LinkStatisticRequest(
    val linkId: Long,
    val timestamp: LocalDateTime,
    val ipAddress: String,
    val country: String?,
    val city: String?,
    val deviceType: String?
)

fun LinkStatisticRequest.toEntity(link: Link): LinkStatistic {
    return LinkStatistic(
        link = link,
        timestamp = this.timestamp,
        ipAddress = this.ipAddress,
        country = this.country ?: "неизвестно",
        city = this.city ?: "неизвестно",
        deviceType = this.deviceType ?: "неизвестно"
    )
}
