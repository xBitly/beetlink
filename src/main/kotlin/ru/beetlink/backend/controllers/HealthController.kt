package ru.beetlink.backend.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("api/v1/health")
class HealthController {
    @GetMapping(value = ["", "/"])
    @ResponseBody
    fun health() = "OK"
}