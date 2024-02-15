package ru.beetlink.backend.models.entity.link


import ru.beetlink.backend.models.entity.AbstractEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "link_statistics")
class LinkStatistic(
    @ManyToOne
    @JoinColumn(name = "link_id")
    var link: Link,

    @Column(name = "ip_address")
    var ipAddress: String,

    @Column(name = "language")
    var language: String,

    @Column(name = "device_type")
    var deviceType: String,

    @Column(name = "referer")
    var referer: String
) : AbstractEntity() {
}
