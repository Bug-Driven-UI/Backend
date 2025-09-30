package ru.hits.bdui.admin.externalApi.database.entity

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Converter
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.stereotype.Component
import ru.hits.bdui.common.dbconverters.JsonConverter
import ru.hits.bdui.common.dbconverters.StringSetConverter
import ru.hits.bdui.common.dbconverters.createJsonConverter
import ru.hits.bdui.common.dbconverters.createJsonListConverter
import ru.hits.bdui.domain.api.Endpoint
import java.util.UUID

@Entity
@Table(name = "external_apis")
data class ApiRepresentationEntity(
    @Id
    val id: UUID,
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
    @Column(name = "schema", nullable = true)
    val schema: ApiSchema?,
    @Column(name = "mapping_script", nullable = true)
    val mappingScript: String?,
    @Column(name = "created_at", nullable = false)
    val createdAt: Long,
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Long,
) {
    companion object
}

@Converter
@Component
class EndpointConverter(objectMapper: ObjectMapper) : JsonConverter<List<Endpoint>> by createJsonListConverter<Endpoint>(objectMapper)

@Converter
@Component
class SchemaConverter(objectMapper: ObjectMapper) : JsonConverter<ApiSchema> by createJsonConverter(objectMapper)
