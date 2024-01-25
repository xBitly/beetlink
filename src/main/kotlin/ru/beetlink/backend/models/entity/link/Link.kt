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

    @Column(name = "original_url", nullable = false)
    var originalUrl: String,

    @Column(name = "description")
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
        val alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

        var number = this.id
        val base = 62
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
    }
}