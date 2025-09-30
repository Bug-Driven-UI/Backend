package ru.hits.bdui.admin.screen.controller.raw.get

import java.util.UUID

data class GetByNameResponseRaw(
    val screenNames: List<ScreenNameRaw>
) {
    data class ScreenNameRaw(
        val id: UUID,
        val name: String,
        val description: String,
    )
}