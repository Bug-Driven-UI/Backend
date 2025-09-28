package ru.hits.bdui.admin.colorStyles.database.emerge

import ru.hits.bdui.admin.colorStyles.database.entity.ColorStyleEntity
import ru.hits.bdui.domain.screen.styles.color.ColorStyle
import ru.hits.bdui.domain.screen.styles.color.ColorStyleFromDatabase
import java.util.UUID

fun ColorStyleEntity.Companion.emerge(colorStyle: ColorStyle): ColorStyleEntity =
    ColorStyleEntity(
        id = UUID.randomUUID(),
        token = colorStyle.token,
        color = colorStyle.color
    )

fun ColorStyleEntity.Companion.emerge(data: ColorStyleFromDatabase): ColorStyleEntity =
    ColorStyleEntity(
        id = data.id,
        token = data.colorStyle.token,
        color = data.colorStyle.color
    )

fun ColorStyleFromDatabase.Companion.emerge(entity: ColorStyleEntity): ColorStyleFromDatabase =
    ColorStyleFromDatabase(
        id = entity.id,
        colorStyle = ColorStyle(
            token = entity.token,
            color = entity.color
        )
    )