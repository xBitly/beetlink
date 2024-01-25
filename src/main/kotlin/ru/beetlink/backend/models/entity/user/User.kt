package ru.beetlink.backend.models.entity.user

import ru.beetlink.backend.models.entity.AbstractEntity
import jakarta.persistence.*
import ru.beetlink.backend.models.entity.link.Link

@Entity
@Table(name = "users")
class User(
    @Column(name = "email")
    var email: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "role")
    var role: Role = Role.USER,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var links: MutableList<Link> = mutableListOf()
) : AbstractEntity() {
    fun addLink(link: Link) {
        links.add(link)
        link.user = this
    }
}