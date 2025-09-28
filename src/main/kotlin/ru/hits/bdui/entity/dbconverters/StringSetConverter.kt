package ru.hits.bdui.entity.dbconverters

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

private const val SEPARATOR = ","

@Converter
class StringSetConverter : AttributeConverter<Set<String>, String> {

    override fun convertToDatabaseColumn(from: Set<String>?): String? {
        return from?.joinToString(SEPARATOR)
    }

    override fun convertToEntityAttribute(from: String?): Set<String>? {
        return from?.split(SEPARATOR)?.toSet()
    }
}
