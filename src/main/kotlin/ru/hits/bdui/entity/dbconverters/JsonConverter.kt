package ru.hits.bdui.entity.dbconverters

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter

interface JsonConverter<T> : AttributeConverter<T, String>

inline fun <reified T> createJsonConverter(objectMapper: ObjectMapper) = object : JsonConverter<T> {

    override fun convertToDatabaseColumn(from: T?): String? {
        return from?.let { objectMapper.writeValueAsString(it) }
    }

    override fun convertToEntityAttribute(from: String?): T? {
        return from?.let { objectMapper.readValue(it, T::class.java) }
    }
}

inline fun <reified T> createJsonListConverter(objectMapper: ObjectMapper) = object : JsonConverter<List<T>> {

    override fun convertToDatabaseColumn(from: List<T>?): String? {
        return from?.let { objectMapper.writeValueAsString(it) }
    }

    override fun convertToEntityAttribute(from: String?): List<T>? {
        return from?.let {
            objectMapper.readValue(it, objectMapper.typeFactory.constructCollectionType(List::class.java, T::class.java))
        }
    }
}
