package ru.hits.bdui.domain.screen

import ru.hits.bdui.domain.screen.meta.ScreenMetaFromDatabase
import java.util.UUID

/**
 * Обертка экрана для ответа из базы данных
 *
 * @property meta мета данные экрана
 * @property versionId идентификатор версии
 * @property version номер версии
 * @property screen экран
 * @property version версия экрана
 * @property createdAtTimestampMs время создания версии
 * @property lastModifiedAtTimestampMs время обновления версии
 */
data class ScreenFromDatabase(
    val meta: ScreenMetaFromDatabase,
    val versionId: UUID,
    val version: Int,
    val screen: Screen,
    val createdAtTimestampMs: Long,
    val lastModifiedAtTimestampMs: Long?,
    val rowVersion: Long?
) {
    companion object
}