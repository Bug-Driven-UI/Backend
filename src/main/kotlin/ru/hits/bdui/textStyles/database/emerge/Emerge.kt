package ru.hits.bdui.textStyles.database.emerge

import ru.hits.bdui.domain.screen.styles.TextStyle
import ru.hits.bdui.textStyles.database.entity.TextStyleEntity
import java.util.UUID

fun TextStyleEntity.Companion.emerge(textStyle: TextStyle): TextStyleEntity =
    TextStyleEntity.emerge(
        id = UUID.randomUUID(),
        textStyle = textStyle
    )

fun TextStyleEntity.Companion.emerge(id: UUID, textStyle: TextStyle): TextStyleEntity =
    TextStyleEntity(
        id = id,
        token = textStyle.token,
        decoration = textStyle.decoration,
        weight = textStyle.weight,
        size = textStyle.size,
        lineHeight = textStyle.lineHeight,
    )