package ru.beetlink.backend.models.entity.link

import ru.beetlink.backend.models.entity.AbstractEntity
import jakarta.persistence.*
import ru.beetlink.backend.models.entity.user.User

@Entity
@Table(name = "links")
class Link(
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @Column(name = "original_url")
    var originalUrl: String,

    @Column(name = "short_id", unique = true, nullable = false, length = 6)
    var shortId: String
) : AbstractEntity() {
}