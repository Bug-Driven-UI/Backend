package ru.hits.bdui.admin.templates.controller.raw

import ru.hits.bdui.common.models.admin.raw.utils.toRaw
import ru.hits.bdui.domain.template.ComponentTemplateFromDatabase

fun ComponentTemplateRaw.Companion.of(data: ComponentTemplateFromDatabase): ComponentTemplateRaw =
    ComponentTemplateRaw(
        id = data.id,
        name = data.template.name.value,
        component = data.template.component.toRaw(),
        createdAtTimestampMs = data.createdAtTimestampMs,
        lastModifiedAtTimestampMs = data.lastModifiedTimestampMs,
    )