package ru.hits.bdui.outbox.scheduler.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
@EnableConfigurationProperties(OutboxProperties::class)
class OutboxConfiguration

@ConfigurationProperties("outbox")
data class OutboxProperties(
    val batchSize: Int = 500,
    val lag: Duration = Duration.ofSeconds(5),
    val windowSize: Duration = Duration.ofMinutes(5)
)