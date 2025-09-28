package ru.hits.bdui.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.hits.bdui.entity.api.ApiRepresentationEntity
import java.util.UUID

@Repository
interface ApiRepresentationRepository : JpaRepository<ApiRepresentationEntity, UUID> {

    @Query("SELECT a FROM ApiRepresentationEntity a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(a.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    fun findByNameOrDescriptionContaining(@Param("query") query: String): List<ApiRepresentationEntity>
}
