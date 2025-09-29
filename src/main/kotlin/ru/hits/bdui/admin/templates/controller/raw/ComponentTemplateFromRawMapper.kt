package ru.hits.bdui.admin.templates.controller.raw

import ru.hits.bdui.admin.templates.models.ComponentTemplateUpdateCommand
import ru.hits.bdui.domain.TemplateName
import ru.hits.bdui.domain.screen.components.Component
import ru.hits.bdui.domain.template.ComponentTemplate
import java.util.UUID

data class UpdateComponentTemplateData(
    val id: UUID,
    val name: String,
    val component: Component
)

object ComponentTemplateFromRawMapper {
    fun ComponentTemplate(name: String, component: Component): ComponentTemplate =
        ComponentTemplate(
            name = TemplateName(name),
            component = component
        )

    fun ComponentTemplateUpdateCommand(data: UpdateComponentTemplateData): ComponentTemplateUpdateCommand =
        ComponentTemplateUpdateCommand(
            id = data.id,
            name = data.name,
            component = data.component,
        )
}