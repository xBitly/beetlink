package ru.beetlink.backend.utils.exception

import org.springframework.http.HttpStatus
import ru.beetlink.backend.utils.exception.AbstractApiException

class NotFoundException(message: String) : AbstractApiException(
    status = HttpStatus.NOT_FOUND,
    message = message
)
