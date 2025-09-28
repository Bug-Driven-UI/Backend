package ru.hits.bdui.textStyles.database.emerge

import ru.hits.bdui.domain.screen.styles.text.TextStyle
import ru.hits.bdui.domain.screen.styles.text.TextStyleFromDatabase
import ru.hits.bdui.textStyles.database.entity.TextStyleEntity
import java.util.UUID

fun TextStyleEntity.Companion.emerge(textStyle: TextStyle): TextStyleEntity =
    TextStyleEntity(
        id = UUID.randomUUID(),
        token = textStyle.token,
        decoration = textStyle.decoration,
        weight = textStyle.weight,
        size = textStyle.size,
        lineHeight = textStyle.lineHeight,
    )

fun TextStyleEntity.Companion.emerge(data: TextStyleFromDatabase): TextStyleEntity =
    TextStyleEntity(
        id = data.id,
        token = data.textStyle.token,
        decoration = data.textStyle.decoration,
        weight = data.textStyle.weight,
        size = data.textStyle.size,
        lineHeight = data.textStyle.lineHeight,
    )

fun TextStyleFromDatabase.Companion.emerge(entity: TextStyleEntity): TextStyleFromDatabase =
    TextStyleFromDatabase(
        id = entity.id,
        textStyle = TextStyle(
            token = entity.token,
            decoration = entity.decoration,
            weight = entity.weight,
            size = entity.size,
            lineHeight = entity.lineHeight
        )
    )