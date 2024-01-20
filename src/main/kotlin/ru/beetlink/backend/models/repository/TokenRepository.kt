package ru.beetlink.backend.models.repository

import ru.beetlink.backend.models.entity.token.Token
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : CrudRepository<Token, Long> {
    fun getTokenByUserId(userId: Long): Token?
    fun removeTokenByUserId(userId: Long)
}