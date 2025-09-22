package ru.hits.bdui.health

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import ru.hits.bdui.controller.v2.HealthControllerV2

@WebFluxTest(controllers = [HealthControllerV2::class])
class HealthControllerV2Test(@Autowired val webTestClient: WebTestClient) {

    @Test
    fun `should return health status for v2`() {
        webTestClient.get()
            .uri("/api/v2/health")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo("ok")
            .jsonPath("$.version").isEqualTo("v2")
    }
}