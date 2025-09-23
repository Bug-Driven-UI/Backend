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
 * @property screenName название экрана
 * @property version версия экрана
 * @property screenNavigationParams параметры, требующиеся для загрузки данного экрана
 * @property apis внешние api, требуемые для загрузки данного экрана
 * @property component корневой компонент экрана
 * @property scaffold скаффолд экрана
 * @property loaders загрузочные экраны для навигации
 * @property textStyles словарь стилей текстов
 * @property colorStyles словарь стилей цветов
 */
data class Screen(
    val screenName: ScreenName,
    val version: ScreenVersion,
    val screenNavigationParams: Set<String>,
    val apis: Map<String, ShortApiRepresentation>,
    val component: Component,
    val scaffold: Scaffold,
    val loaders: Map<ScreenName, Loader>,
    val textStyles: Map<String, TextStyle>,
    val colorStyles: Map<String, ColorStyle>
)