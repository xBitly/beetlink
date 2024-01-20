package ru.beetlink.backend.utils.exception

import org.springframework.http.HttpStatus
import ru.beetlink.backend.utils.exception.AbstractApiException

class InvalidRefreshTokenException : AbstractApiException(
    status = HttpStatus.CONFLICT,
    message = "ошибка авторизации"
)