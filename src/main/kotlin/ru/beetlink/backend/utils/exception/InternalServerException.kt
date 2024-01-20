package ru.beetlink.backend.utils.exception

import org.springframework.http.HttpStatus
import ru.beetlink.backend.utils.exception.AbstractApiException

class InternalServerException : AbstractApiException(
    status = HttpStatus.INTERNAL_SERVER_ERROR,
    message = "что-то пошло не так"
)
