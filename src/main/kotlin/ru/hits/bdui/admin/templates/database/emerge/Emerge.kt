package ru.hits.bdui.admin.templates.database.emerge

import ru.hits.bdui.admin.templates.database.entity.TemplateEntity
import ru.hits.bdui.domain.TemplateName
import ru.hits.bdui.domain.template.ComponentTemplate
import ru.hits.bdui.domain.template.ComponentTemplateFromDatabase
import java.time.Instant
import java.util.UUID

fun TemplateEntity.Companion.emerge(template: ComponentTemplate): TemplateEntity =
    TemplateEntity(
        id = UUID.randomUUID(),
        name = template.name.value,
        component = template.component,
        createdAtTimestampMs = Instant.now().toEpochMilli(),
        lastModifiedAtTimestampMs = null,
    )

fun TemplateEntity.Companion.emerge(data: ComponentTemplateFromDatabase): TemplateEntity =
    TemplateEntity(
        id = data.id,
        name = data.template.name.value,
        component = data.template.component,
        createdAtTimestampMs = data.createdAtTimestampMs,
        lastModifiedAtTimestampMs = data.lastModifiedTimestampMs,
    )

fun ComponentTemplateFromDatabase.Companion.emerge(entity: TemplateEntity): ComponentTemplateFromDatabase =
    ComponentTemplateFromDatabase(
        id = entity.id,
        template = ComponentTemplate(
            name = TemplateName(entity.name),
            component = entity.component,
        ),
        createdAtTimestampMs = entity.createdAtTimestampMs,
        lastModifiedTimestampMs = entity.lastModifiedAtTimestampMs
    )