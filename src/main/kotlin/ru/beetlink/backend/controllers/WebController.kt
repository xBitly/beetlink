package ru.beetlink.backend.controllers

import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import ru.beetlink.backend.models.dto.request.link.LinkStatisticRequest
import ru.beetlink.backend.models.dto.response.user.UserInfo
import ru.beetlink.backend.services.LinkService
import ru.beetlink.backend.services.UserService
import ru.beetlink.backend.utils.exception.NotFoundException

@Controller
class WebController (
    @Autowired private val linkService: LinkService
) {
    @GetMapping(value = ["", "/"])
    fun showLanding(): String {
        return "index"
    }

    @GetMapping("/links/{id}")
    fun showLink(): String {
        return "link"
    }

    @GetMapping("/create")
    fun showCreate(): String {
        return "create"
    }

    @GetMapping("/signin")
    fun showSignIn(): String {
        return "signin"
    }

    @GetMapping("/signup")
    fun showSignUp(): String {
        return "signup"
    }

    @GetMapping("/home")
    fun showHome(): String {
        return "home"
    }

    @GetMapping("/terms")
    fun showTerms(): String {
        return "terms"
    }

    @GetMapping("/privacy")
    fun showPrivacy(): String {
        return "privacy"
    }

    @GetMapping("/error")
    fun showError(): String {
        return "error"
    }

    @GetMapping("/{shortId}")
    fun redirectToOriginalLink(
        @PathVariable shortId: String,
        request: HttpServletRequest
    ): ResponseEntity<Void> {
        return ResponseEntity.status(HttpStatus.FOUND)
            .header(HttpHeaders.LOCATION, linkService.getLinkByShortId(shortId, request))
            .build()
    }

}