package ru.hits.bdui.outbox.scheduler.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(ScreenOutboxProperties::class)
class ScreenOutboxConfiguration

@ConfigurationProperties(value = "outbox.screen")
class ScreenOutboxProperties(
    val updated: TopicConfig,
    val created: TopicConfig,
)