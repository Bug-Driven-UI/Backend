package ru.hits.bdui.common.components.validation

import ru.hits.bdui.domain.screen.styles.color.ColorStyle
import ru.hits.bdui.domain.screen.styles.text.TextStyle
import ru.hits.bdui.domain.template.ComponentTemplate

class MappingContext(
    private val colors: Map<String, ColorStyle>,
    private val texts: Map<String, TextStyle>,
    private val templates: Map<String, ComponentTemplate>,
) {
    fun color(token: String): ColorStyle = colors.getValue(token)
    fun text(token: String): TextStyle = texts.getValue(token)
    fun template(name: String): ComponentTemplate = templates.getValue(name)
}