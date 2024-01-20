package ru.beetlink.backend.utils.exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import ru.beetlink.backend.models.dto.response.ApiResponse
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@JsonIgnoreProperties("cause", "stackTrace", "suppressed", "localizedMessage")
abstract class AbstractApiException(
    override val status: HttpStatus,
    override val message: String
) : ApiResponse, Exception() {
    override val timestamp: LocalDateTime = LocalDateTime.now()
}
