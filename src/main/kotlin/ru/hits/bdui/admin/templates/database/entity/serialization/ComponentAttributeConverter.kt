package ru.hits.bdui.admin.templates.database.entity.serialization

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import ru.hits.bdui.common.exceptions.SerializationException
import ru.hits.bdui.domain.screen.components.Component

@Converter(autoApply = false)
class ComponentAttributeConverter : AttributeConverter<Component, String> {
    private val objectMapper = jacksonObjectMapper()
        .apply {
            addMixIn(Component::class.java, ComponentMixIn::class.java)
        }

    override fun convertToDatabaseColumn(attribute: Component): String =
        runCatching { objectMapper.writeValueAsString(attribute) }
            .getOrElse { throw SerializationException("Не удалось сериализовать компонент") }

    override fun convertToEntityAttribute(attribute: String): Component =
        runCatching {
            objectMapper.readValue<Component>(attribute)
        }
            .getOrElse { throw SerializationException("Не удалось десериализовать компонент") }
}