package ru.hits.bdui.entity.api

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.*
import org.springframework.stereotype.Component
import ru.hits.bdui.domain.api.Endpoint
import ru.hits.bdui.domain.api.schema.Schema
import ru.hits.bdui.entity.dbconverters.JsonConverter
import ru.hits.bdui.entity.dbconverters.StringSetConverter
import ru.hits.bdui.entity.dbconverters.createJsonConverter
import ru.hits.bdui.entity.dbconverters.createJsonListConverter
import java.util.*

@Entity
@Table(name = "externalApis")
class ApiRepresentationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "description", nullable = false)
    val description: String,
    @Convert(converter = StringSetConverter::class)
    @Column(name = "params", nullable = false)
    val params: Set<String>,
    @Convert(converter = EndpointConverter::class)
    @Column(name = "endpoints", nullable = false)
    val endpoints: List<Endpoint>,
    @Convert(converter = SchemaConverter::class)
    @Column(name = "schema", nullable = false)
    val schema: Schema,
    @Column(name = "mappingScript", nullable = false)
    val mappingScript: String,
    @Column(name = "createdAt", nullable = false)
    val createdAt: Long,
    @Column(name = "updatedAt", nullable = false)
    val updatedAt: Long,
) {
    fun copy(
        id: UUID? = this.id,
        name: String = this.name,
        description: String = this.description,
        params: Set<String> = this.params,
        endpoints: List<Endpoint> = this.endpoints,
        schema: Schema = this.schema,
        mappingScript: String = this.mappingScript,
        createdAt: Long = this.createdAt,
        updatedAt: Long = this.updatedAt,
    ) = ApiRepresentationEntity(
        id = id,
        name = name,
        description = description,
        params = params,
        endpoints = endpoints,
        schema = schema,
        mappingScript = mappingScript,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

@Converter
@Component
class EndpointConverter(objectMapper: ObjectMapper) : JsonConverter<List<Endpoint>> by createJsonListConverter<Endpoint>(objectMapper)

@Converter
@Component
class SchemaConverter(objectMapper: ObjectMapper) : JsonConverter<Schema> by createJsonConverter(objectMapper)
