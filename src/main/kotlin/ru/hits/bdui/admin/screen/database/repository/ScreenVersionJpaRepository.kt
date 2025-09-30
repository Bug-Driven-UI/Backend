package ru.hits.bdui.admin.screen.database.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.hits.bdui.admin.screen.database.entity.ScreenVersionEntity
import java.util.UUID

interface ScreenVersionJpaRepository : JpaRepository<ScreenVersionEntity, UUID> {
}