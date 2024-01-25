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

    @Column(name = "timestamp")
    var timestamp: LocalDateTime,

    @Column(name = "ip_address")
    var ipAddress: String,

    @Column(name = "country")
    var country: String,

    @Column(name = "city")
    var city: String,

    @Column(name = "device_type")
    var deviceType: String
) : AbstractEntity() {
}
