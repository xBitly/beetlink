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
    var description: String,

    @Column(name = "short_id", unique = true, nullable = false)
    var shortId: String,

    @OneToMany(mappedBy = "link", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var statistics: MutableList<LinkStatistic> = mutableListOf()
) : AbstractEntity() {
    init {
        shortId = generateShortId(id)
    }

    fun addStatistic(statistic: LinkStatistic) {
        statistics.add(statistic)
        statistic.link = this
    }

    fun getDailyStatistics(startDate: LocalDate, endDate: LocalDate): List<LinkStatistic> {
        return statistics.filter {
            it.timestamp.toLocalDate().isAfter(startDate.minusDays(1)) &&
                    it.timestamp.toLocalDate().isBefore(endDate.plusDays(1))
        }
    }

    companion object {
        private fun generateShortId(id: Long): String {
            val base62Chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            val result = StringBuilder()
            var currentId = id

            do {
                result.append(base62Chars[(currentId % 62).toInt()])
                currentId /= 62
            } while (currentId > 0)

            return result.reverse().toString()
        }
    }
}