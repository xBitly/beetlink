package ru.beetlink.backend.models.entity.token

import ru.beetlink.backend.models.entity.AbstractEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "tokens")
class Token(
    @Column(name = "user_id")
    var userId: Long,

    @Column(name = "access_token")
    var accessToken: String,

    @Column(name = "refresh_token")
    var refreshToken: String
) : AbstractEntity()