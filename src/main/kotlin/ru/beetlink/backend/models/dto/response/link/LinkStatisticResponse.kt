package ru.beetlink.backend.models.dto.response.link

import ru.beetlink.backend.models.entity.link.LinkStatistic
import java.time.LocalDateTime

data class LinkStatisticInfo (
    val createdAt: LocalDateTime,
    val ipAddress: String,
    val language: String,
    val deviceType: String
)

fun LinkStatistic.toDto(): LinkStatisticInfo {
    return LinkStatisticInfo(
        createdAt = createdAt,
        ipAddress = ipAddress,
        language = language,
        deviceType = deviceType
    )
}