package ru.beetlink.backend.utils.exception

import org.springframework.http.HttpStatus
import ru.beetlink.backend.utils.exception.AbstractApiException

class EmailAlreadyTakenException : AbstractApiException(
    status = HttpStatus.CONFLICT,
    message = "Email already taken"
)