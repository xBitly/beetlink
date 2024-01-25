package ru.beetlink.backend.services

import ru.beetlink.backend.models.dto.request.auth.AccountInput
import ru.beetlink.backend.models.dto.request.auth.CredentialsRequest
import ru.beetlink.backend.models.dto.request.auth.PasswordRequest
import ru.beetlink.backend.models.dto.request.link.toEntity
import ru.beetlink.backend.models.dto.response.auth.AuthTokenInfo
import ru.beetlink.backend.models.dto.response.auth.toDto
import ru.beetlink.backend.models.dto.response.auth.toEntity
import ru.beetlink.backend.models.entity.user.User
import ru.beetlink.backend.models.repository.TokenRepository
import ru.beetlink.backend.models.repository.UserRepository
import ru.beetlink.backend.utils.exception.*
import ru.beetlink.backend.utils.token.TokenManager
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val tokenRepository: TokenRepository,
    @Autowired private val tokenManager: TokenManager
) {
    fun signup(user: AccountInput): AuthTokenInfo {
        if (userRepository.existsByEmail(user.email)) {
            throw EmailAlreadyTakenException()
        }
        val userEntity = user.toEntity()
        return getToken(userEntity, true)
    }

    @Transactional
    fun signin(request: CredentialsRequest): AuthTokenInfo {
        userRepository.getUserByEmail(request.email)?.let { user ->
            if (user.password != request.password) {
                throw InvalidPasswordException()
            }
            return getToken(user, false)
        }
        throw NotFoundException("пользователь не найден")
    }

    fun refresh(refreshToken: String): AuthTokenInfo {
        if (tokenManager.validateRefreshToken(refreshToken)) {
            val claims = tokenManager.getRefreshClaims(refreshToken)
            val token = tokenRepository.getTokenByUserId(claims.subject.toLong())
            if (token?.refreshToken == refreshToken) {
                userRepository.getUserById(token.userId)?.let { user ->
                    token.accessToken = tokenManager.generateAccessToken(user)
                    token.refreshToken = tokenManager.generateRefreshToken(user)
                }
                tokenRepository.save(token)
                return token.toDto()
            }
        }
        throw InvalidRefreshTokenException()
    }

    @Transactional
    fun signout(userId: Long) = tokenRepository.removeTokenByUserId(userId)

    fun setPassword(userId: Long, request: PasswordRequest) {
        userRepository.getUserById(userId)?.let { user ->
            if (request.oldPassword != user.password) {
                throw InvalidPasswordException()
            }
            user.password = request.newPassword
            userRepository.save(user)
        } ?: run { throw NotFoundException("пользователь не найден") }
    }

    fun getToken(user: User, registration: Boolean): AuthTokenInfo{
        userRepository.save(user)
        val userId = user.id
        tokenRepository.removeTokenByUserId(userId)
        val token = AuthTokenInfo(
            accessToken = tokenManager.generateAccessToken(user),
            refreshToken = tokenManager.generateRefreshToken(user),
            registration = registration
        )
        tokenRepository.save(token.toEntity(userId))
        return token
    }
}