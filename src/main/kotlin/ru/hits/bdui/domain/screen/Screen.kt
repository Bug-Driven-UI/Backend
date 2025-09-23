package ru.hits.bdui.domain.screen

import ru.hits.bdui.domain.ScreenName
import ru.hits.bdui.domain.ScreenVersion
import ru.hits.bdui.domain.api.ShortApiRepresentation
import ru.hits.bdui.domain.screen.components.Component
import ru.hits.bdui.domain.screen.styles.ColorStyle
import ru.hits.bdui.domain.screen.styles.TextStyle

/**
 * Представление экрана
 *
 * @param screenName название экрана
 * @param version версия экрана
 * @param screenNavigationParams параметры, требующиеся для загрузки данного экрана
 * @param apis внешние api, требуемые для загрузки данного экрана
 * @param components список компонентов экрана
 * @param scaffold скаффолд экрана
 * @param loaders загрузочные экраны для навигации
 * @param textStyles словарь стилей текстов
 * @param colorStyles словарь стилей цветов
 */
data class Screen(
    val screenName: ScreenName,
    val version: ScreenVersion,
    val screenNavigationParams: Set<String>,
    val apis: Map<String, ShortApiRepresentation>,
    val components: List<Component>,
    val scaffold: Scaffold,
    val loaders: Map<ScreenName, Loader>,
    val textStyles: Map<String, TextStyle>,
    val colorStyles: Map<String, ColorStyle>
)