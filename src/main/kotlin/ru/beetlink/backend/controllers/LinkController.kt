package ru.beetlink.backend.controllers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.beetlink.backend.models.dto.request.link.LinkRequest
import ru.beetlink.backend.models.dto.response.link.LinkInfo
import ru.beetlink.backend.models.dto.response.link.LinkStatisticInfo
import ru.beetlink.backend.services.LinkService
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/links")
class LinkController(
    @Autowired private val linkService: LinkService
) {

    @PostMapping("/create")
    fun createLink(@RequestBody linkRequest: LinkRequest, auth: Authentication): LinkInfo {
        val userId = auth.principal as Long
        return linkService.createLink(userId, linkRequest)
    }

    @GetMapping("/{linkId}")
    fun getLinkById(@PathVariable linkId: Long, auth: Authentication): LinkInfo {
        val userId = auth.principal as Long
        return linkService.getLinkById(userId, linkId)
    }

    @PutMapping("/{linkId}")
    fun updateLink(
        @PathVariable linkId: Long,
        @RequestBody linkRequest: LinkRequest,
        auth: Authentication
    ){
        val userId = auth.principal as Long
        linkService.updateLink(userId, linkId, linkRequest)
    }

    @DeleteMapping("/{linkId}")
    fun deleteLink(@PathVariable linkId: Long){
        linkService.deleteLink(linkId)
    }

    @GetMapping("/{linkId}/statistics")
    fun getLinkStatistics(
        @PathVariable linkId: Long,
        @RequestParam startDate: String,
        @RequestParam endDate: String,
        auth: Authentication
    ): List<LinkStatisticInfo> {
        val userId = auth.principal as Long
        return linkService.getLinkStatistics(userId, linkId, LocalDate.parse(startDate), LocalDate.parse(endDate))
    }

}
