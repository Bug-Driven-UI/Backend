package ru.hits.bdui.admin.colorStyles.controller.raw

import ru.hits.bdui.domain.screen.styles.color.ColorStyle
import ru.hits.bdui.domain.screen.styles.color.ColorStyleFromDatabase
import java.util.UUID

object ColorStyleFromRawMapper {
    fun ColorStyle(raw: ColorStyleForSaveRaw): ColorStyle =
        ColorStyle(
            token = raw.token,
            color = raw.color
        )

    fun ColorStyleFromDatabase(id: UUID, raw: ColorStyleForSaveRaw): ColorStyleFromDatabase =
        ColorStyleFromDatabase(
            id = id,
            colorStyle = ColorStyle(
                token = raw.token,
                color = raw.color
            )
        )
}