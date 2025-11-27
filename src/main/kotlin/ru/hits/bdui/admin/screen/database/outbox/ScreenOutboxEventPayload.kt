package ru.hits.bdui.admin.screen.database.outbox

import ru.hits.bdui.domain.screen.ScreenFromDatabase
import java.util.UUID

data class ScreenOutboxEventPayload(
    val id: UUID,
    val name: String,
    val versionId: UUID?
) {
    companion object {
        fun emerge(screen: ScreenFromDatabase): ScreenOutboxEventPayload =
            ScreenOutboxEventPayload(
                id = screen.meta.id.value,
                name = screen.meta.name.value,
                versionId = screen.meta.versionId
            )
    }
}