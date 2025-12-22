package ru.hits.bdui

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import ru.hits.bdui.config.ApplicationConfiguration

@SpringBootApplication(scanBasePackageClasses = [ApplicationConfiguration::class])
@EnableScheduling
class BduiApplication

fun main(args: Array<String>) {
    runApplication<BduiApplication>(*args)
}
