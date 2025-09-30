package ru.hits.bdui.admin.colorStyles.database.emerge

import ru.hits.bdui.admin.colorStyles.database.entity.ColorStyleEntity
import ru.hits.bdui.domain.screen.styles.color.ColorStyle
import ru.hits.bdui.domain.screen.styles.color.ColorStyleFromDatabase
import ru.hits.bdui.engine.expression.ExpressionUtils
import java.util.UUID

fun ColorStyleEntity.Companion.emerge(colorStyle: ColorStyle): ColorStyleEntity =
    ColorStyleEntity(
        id = UUID.randomUUID(),
        token = colorStyle.token.value as String,
        color = colorStyle.color.value as String,
    )

fun ColorStyleEntity.Companion.emerge(data: ColorStyleFromDatabase): ColorStyleEntity =
    ColorStyleEntity(
        id = data.id,
        token = data.colorStyle.token.value as String,
        color = data.colorStyle.color.value as String,
    )

fun ColorStyleFromDatabase.Companion.emerge(entity: ColorStyleEntity): ColorStyleFromDatabase =
    ColorStyleFromDatabase(
        id = entity.id,
        colorStyle = ColorStyle(
            token = ExpressionUtils.getValueOrExpression(entity.token),
            color = ExpressionUtils.getValueOrExpression(entity.color),
        )
    )