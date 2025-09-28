package ru.hits.bdui.admin.templates.models

import ru.hits.bdui.domain.screen.components.Component
import java.util.UUID

data class ComponentTemplateUpdateCommand(
    val id: UUID,
    val name: String,
    val component: Component
)