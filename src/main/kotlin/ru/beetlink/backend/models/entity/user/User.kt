package ru.beetlink.backend.models.entity.user

import ru.beetlink.backend.models.entity.AbstractEntity
import jakarta.persistence.*
import ru.beetlink.backend.models.entity.user.Role

@Entity
@Table(name = "users")
class User(
    @Column(name = "email")
    var email: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "role")
    var role: Role = Role.UNCONFIRMED_USER
) : AbstractEntity() {

}