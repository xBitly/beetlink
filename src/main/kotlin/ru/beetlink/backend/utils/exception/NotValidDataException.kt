package ru.beetlink.backend.utils.exception

import org.springframework.http.HttpStatus
import ru.beetlink.backend.utils.exception.AbstractApiException

class NotValidDataException : AbstractApiException(
    status = HttpStatus.CONFLICT,
    message = "неккоректные данные"
)