package ru.hits.bdui.admin.colorStyles.controller.raw

import ru.hits.bdui.domain.screen.styles.color.ColorStyleFromDatabase

fun ColorStyleRaw.Companion.emerge(data: ColorStyleFromDatabase): ColorStyleRaw =
    ColorStyleRaw(
        id = data.id,
        token = data.colorStyle.token.value as String,
        color = data.colorStyle.color.value as String,
    )