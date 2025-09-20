package ru.hits.bdui

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.hits.bdui.config.ApplicationConfiguration

@SpringBootApplication(scanBasePackageClasses = [ApplicationConfiguration::class])
class BduiApplication

fun main(args: Array<String>) {
    runApplication<BduiApplication>(*args)
}
