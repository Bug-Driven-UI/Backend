package ru.hits.bdui.health

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import ru.hits.bdui.controller.v1.HealthControllerV1

@WebFluxTest(controllers = [HealthControllerV1::class])
class HealthControllerV1Test(@Autowired val webTestClient: WebTestClient) {

    @Test
    fun `should return health status for v1`() {
        webTestClient.get()
            .uri("/api/v1/health")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo("ok")
            .jsonPath("$.version").isEqualTo("v1")
    }
}