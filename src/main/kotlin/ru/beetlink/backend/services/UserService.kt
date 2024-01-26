package ru.beetlink.backend.services

import jakarta.transaction.Transactional
import ru.beetlink.backend.models.dto.response.user.UserInfo
import ru.beetlink.backend.models.dto.response.user.toDto
import ru.beetlink.backend.models.entity.user.Role
import ru.beetlink.backend.models.entity.user.User
import ru.beetlink.backend.models.repository.UserRepository
import ru.beetlink.backend.utils.exception.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.beetlink.backend.models.dto.response.link.LinkInfo
import ru.beetlink.backend.models.dto.response.link.toDto
import java.io.File
import java.util.*

@Service
class UserService(
    @Autowired private val userRepository: UserRepository
) {

    @Transactional
    fun getUserLinks(userId: Long): List<LinkInfo> {
        return userRepository.getUserById(userId)?.let { user ->
            user.links.map { it.toDto() }
        } ?: run {
            throw NotFoundException("пользователь не найден")
        }
    }

    fun getUserById(userId: Long, selfId: Long): UserInfo {
        return userRepository.getUserById(userId)?.let { user ->
            toDtoWrapper(user, selfId)
        } ?: run {
            throw NotFoundException("пользователь не найден")
        }
    }

    fun setUserRole(userId: Long, role: Role) {
        userRepository.getUserById(userId)?.let { user ->
            user.role = role
            userRepository.save(user)
        } ?: throw NotFoundException("пользователь не найден")
    }

//    fun setUserProfile(userId: Long, request: ProfileRequest) {
//        userRepository.getUserById(userId)?.let { user ->
//            user.username = request.username
//            userRepository.save(user)
//        } ?: throw NotFoundException("User with id$userId is not found")
//    }

    private fun toDtoWrapper(user: User, selfId: Long): UserInfo {
        val userId = user.id
        return user.toDto(
            userId == selfId
        )
    }
}