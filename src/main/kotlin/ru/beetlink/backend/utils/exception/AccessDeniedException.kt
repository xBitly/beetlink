package ru.beetlink.backend.utils.exception

import org.springframework.http.HttpStatus
import ru.beetlink.backend.utils.exception.AbstractApiException

class AccessDeniedException : AbstractApiException(
    status = HttpStatus.FORBIDDEN,
    message = "Access denied"
)