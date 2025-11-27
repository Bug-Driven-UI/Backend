package ru.hits.bdui.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["ru.hits.bdui.admin", "ru.hits.bdui.common", "ru.hits.bdui.outbox"])
class AdminConfiguration