package ru.beetlink.backend.models.entity.link

import ru.beetlink.backend.models.entity.AbstractEntity
import jakarta.persistence.*
import ru.beetlink.backend.models.entity.user.User
import java.time.LocalDate

@Entity
@Table(name = "links")
class Link(
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @Column(name = "ios_url")
    var iosUrl: String?,

    @Column(name = "android_url")
    var androidUrl: String?,

    @Column(name = "desktop_url")
    var desktopUrl: String?,

    @Column(name = "default_url", nullable = false)
    var defaultUrl: String,

    @Column(name = "description", length = 255)
    var description: String?,

    @Column(name = "short_id", unique = true)
    var shortId: String?,

    @OneToMany(mappedBy = "link", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var statistics: MutableList<LinkStatistic> = mutableListOf()
) : AbstractEntity() {
    fun addStatistic(statistic: LinkStatistic) {
        statistics.add(statistic)
        statistic.link = this
    }

    fun getDailyStatistics(startDate: LocalDate, endDate: LocalDate): List<LinkStatistic> {
        return statistics.filter {
            it.createdAt.toLocalDate().isAfter(startDate.minusDays(1)) &&
                    it.createdAt.toLocalDate().isBefore(endDate.plusDays(1))
        }
    }

    fun generateShortId() {
        val alphabet = "0123456789abcdefghijklmnopqrstuvwxyz"

        var number = this.id
        val base = 36
        val sb = StringBuilder()

        if (number == 0L) {
            shortId = alphabet[0].toString()
            return
        }

        while (number > 0) {
            val remainder = (number % base).toInt()
            sb.append(alphabet[remainder])
            number /= base
        }

        shortId = sb.reverse().toString()
    } //TODO: exist by path in web
}