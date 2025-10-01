package ru.hits.bdui.client.action.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class ActionController {
    @PostMapping("/v1/screen/action")
    fun handleAction(): Mono<>
}