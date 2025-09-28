package ru.hits.bdui.admin.colorStyles.controller.raw

import ru.hits.bdui.domain.screen.styles.color.ColorStyleFromDatabase

fun ColorStyleRaw.Companion.of(data: ColorStyleFromDatabase): ColorStyleRaw =
    ColorStyleRaw(
        id = data.id,
        token = data.colorStyle.token,
        color = data.colorStyle.color
    )