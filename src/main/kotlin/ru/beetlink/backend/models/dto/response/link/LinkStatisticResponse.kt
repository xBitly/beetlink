package ru.beetlink.backend.models.dto.response.link

import ru.beetlink.backend.models.entity.link.LinkStatistic
import java.time.LocalDateTime

data class LinkStatisticInfo (
    val timestamp: LocalDateTime,
    val ipAddress: String,
    val country: String,
    val city: String,
    val deviceType: String
)

fun LinkStatistic.toDto(): LinkStatisticInfo {
    return LinkStatisticInfo(
        timestamp = timestamp,
        ipAddress = ipAddress,
        country = country,
        city = city,
        deviceType = deviceType
    )
}