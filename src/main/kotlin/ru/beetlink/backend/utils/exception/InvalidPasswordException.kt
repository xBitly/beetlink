package ru.beetlink.backend.utils.exception

import org.springframework.http.HttpStatus
import ru.beetlink.backend.utils.exception.AbstractApiException

class InvalidPasswordException : AbstractApiException(
    status = HttpStatus.CONFLICT,
    message = "Invalid password"
)