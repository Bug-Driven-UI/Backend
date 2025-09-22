package ru.hits.bdui.controller.v2

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v2")
class HealthControllerV2 {
    @GetMapping("/health")
    fun health(): Mono<Map<String, String>> =
        Mono.just(mapOf("status" to "ok", "version" to "v2"))
}