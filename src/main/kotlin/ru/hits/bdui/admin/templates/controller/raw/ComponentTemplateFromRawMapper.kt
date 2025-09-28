package ru.hits.bdui.admin.templates.controller.raw

import ru.hits.bdui.admin.templates.models.ComponentTemplateUpdateCommand
import ru.hits.bdui.domain.TemplateName
import ru.hits.bdui.domain.template.ComponentTemplate
import java.util.UUID

object ComponentTemplateFromRawMapper {
    fun ComponentTemplate(raw: ComponentTemplateForSaveRaw): ComponentTemplate =
        ComponentTemplate(
            name = TemplateName(raw.name),
            component = raw.component
        )

    fun ComponentTemplateUpdateCommand(id: UUID, raw: ComponentTemplateForSaveRaw): ComponentTemplateUpdateCommand =
        ComponentTemplateUpdateCommand(
            id = id,
            name = raw.name,
            component = raw.component,
        )
}