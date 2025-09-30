package ru.hits.bdui.admin.colorStyles.controller.raw

import ru.hits.bdui.domain.screen.styles.color.ColorStyle
import ru.hits.bdui.domain.screen.styles.color.ColorStyleFromDatabase
import ru.hits.bdui.engine.expression.ExpressionUtils
import java.util.UUID

object ColorStyleFromRawMapper {
    fun ColorStyle(raw: ColorStyleForSaveRaw): ColorStyle =
        ColorStyle(
            token = ExpressionUtils.getValueOrExpression(raw.token),
            color = ExpressionUtils.getValueOrExpression(raw.color),
        )

    fun ColorStyleFromDatabase(id: UUID, raw: ColorStyleForSaveRaw): ColorStyleFromDatabase =
        ColorStyleFromDatabase(
            id = id,
            colorStyle = ColorStyle(
                token = ExpressionUtils.getValueOrExpression(raw.token),
                color = ExpressionUtils.getValueOrExpression(raw.color),
            )
        )
}