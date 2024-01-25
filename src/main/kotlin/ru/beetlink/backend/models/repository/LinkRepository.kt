package ru.beetlink.backend.models.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.beetlink.backend.models.entity.link.Link
import ru.beetlink.backend.models.entity.user.User

@Repository
interface LinkRepository: CrudRepository<Link, Long> {
    fun getLinkById(id: Long): Link?
    fun getLinkByShortId(shortId: String): Link?
    fun removeLinkById(id: Long)
}