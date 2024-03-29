package ru.beetlink.backend.services

import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.beetlink.backend.models.dto.request.link.LinkRequest
import ru.beetlink.backend.models.dto.request.link.toEntity
import ru.beetlink.backend.models.dto.response.link.LinkInfo
import ru.beetlink.backend.models.dto.response.link.LinkStatisticInfo
import ru.beetlink.backend.models.dto.response.link.toDto
import ru.beetlink.backend.models.entity.link.LinkStatistic
import ru.beetlink.backend.models.repository.LinkRepository
import ru.beetlink.backend.models.repository.UserRepository
import ru.beetlink.backend.utils.exception.NotFoundException
import ru.beetlink.backend.utils.exception.AccessDeniedException
import java.time.LocalDate

@Service
class LinkService(
    @Autowired private val linkRepository: LinkRepository,
    @Autowired private val userRepository: UserRepository
) {

    fun createLink(userId: Long, linkRequest: LinkRequest): LinkInfo {
        userRepository.getUserById(userId)?.let { user ->
            val link = linkRepository.save(linkRequest.toEntity(user))
            link.generateShortId()
            val savedLink = linkRepository.save(link)
            return savedLink.toDto()
        } ?: throw NotFoundException("пользователь не найден")
    }

    @Transactional
    fun getLinkByShortId(shortId: String, request: HttpServletRequest): String {
        linkRepository.getLinkByShortId(shortId)?.let { link ->
            val statistic = LinkStatistic(
                link = link,
                ipAddress = getClientIp(request),
                language = detectUserLanguage(request),
                deviceType = detectDeviceType(request) ?: "неизвестно",
                referer = detectReferer(request) ?: "прямое посещение"
            )
            link.addStatistic(statistic)
            return when (statistic.deviceType) {
                "ios" -> link.iosUrl ?: link.defaultUrl
                "android" -> link.androidUrl ?: link.defaultUrl
                "desktop" -> link.desktopUrl ?: link.defaultUrl
                else -> link.defaultUrl
            }
        } ?: throw NotFoundException("ссылка не найдена")
    }

    private fun getClientIp(request: HttpServletRequest): String {
        val xForwardedFor = request.getHeader("X-Forwarded-For")
        return if (xForwardedFor.isNullOrEmpty()) {
            request.remoteAddr
        } else {
            xForwardedFor.split(",").firstOrNull()?.trim() ?: request.remoteAddr
        }
    }

    private fun detectDeviceType(request: HttpServletRequest): String? {
        val userAgent = request.getHeader("User-Agent")?.lowercase()
        return when {
            userAgent?.contains("android") == true -> "android"
            userAgent?.contains("iphone") == true || userAgent?.contains("ipad") == true -> "ios"
            userAgent?.contains("macintosh") == true || userAgent?.contains("mac os") == true || userAgent?.contains("windows") == true -> "desktop"
            else -> null
        }
    }

    private fun detectReferer(request: HttpServletRequest): String? {
        val referer = request.getHeader("Referer")?.lowercase()

        val queryString = request.queryString
        if (queryString != null) {
            val params = queryString.split("&")
            for (param in params) {
                val keyValue = param.split("=")
                if (keyValue.size == 2 && keyValue[0] == "utm_source") {
                    return keyValue[1].lowercase()
                }
            }
        }

        return referer?.lowercase()
    }


    private fun detectUserLanguage(request: HttpServletRequest): String {
        return request.getHeader("Accept-Language")?.substring(0, 2)?.lowercase() ?: "неизвестно"
    }


    fun getLinkById(userId: Long, linkId: Long): LinkInfo {
        linkRepository.getLinkById(linkId)?.let { link ->
            if (link.user.id == userId) {
                return link.toDto()
            } else {
                throw AccessDeniedException()
            }
        } ?: throw NotFoundException("ссылка не найдена")
    }

    fun updateLink(userId: Long, linkId: Long, linkRequest: LinkRequest) {
        linkRepository.getLinkById(linkId)?.let { link ->
            if (link.user.id == userId) {
                link.iosUrl = linkRequest.iosUrl
                link.androidUrl = linkRequest.androidUrl
                link.desktopUrl = linkRequest.desktopUrl
                link.defaultUrl = linkRequest.defaultUrl
                link.description = linkRequest.description
                linkRepository.save(link)
            } else {
                throw AccessDeniedException()
            }
        } ?: throw NotFoundException("ссылка не найдена")
    }

    @Transactional
    fun deleteLink(linkId: Long) = linkRepository.removeLinkById(linkId)

    @Transactional
    fun getLinkStatistics(userId: Long, linkId: Long, startDate: LocalDate, endDate: LocalDate): List<LinkStatisticInfo> {
        linkRepository.getLinkById(linkId)?.let { link ->
            if (link.user.id == userId) {
                return link.getDailyStatistics(startDate, endDate).map { it.toDto() }
            } else {
                throw AccessDeniedException()
            }
        } ?: throw NotFoundException("ссылка не найдена")
    }
}