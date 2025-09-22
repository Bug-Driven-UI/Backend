package ru.hits.bdui

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.hits.bdui.config.ApplicationConfiguration
import ru.hits.bdui.controller.v1.HealthControllerV1
import ru.hits.bdui.controller.v2.HealthControllerV2

@SpringBootApplication (scanBasePackageClasses = [ApplicationConfiguration::class, HealthControllerV1::class, HealthControllerV2::class])
class BduiApplication

fun main(args: Array<String>) {
    runApplication<BduiApplication>(*args)
}
