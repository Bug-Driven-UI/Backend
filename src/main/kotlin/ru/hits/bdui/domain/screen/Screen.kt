package ru.hits.bdui.domain.screen

import ru.hits.bdui.domain.ScreenName
import ru.hits.bdui.domain.api.ShortApiRepresentation
import ru.hits.bdui.domain.screen.components.Component

/**
 * Представление экрана
 *
 * @property screenName название экрана
 * @property description краткое описание экрана
 * @property screenNavigationParams параметры, требующиеся для загрузки данного экрана
 * @property apis внешние API, требуемые для загрузки данного экрана, где ключ - alias для обращения к данным из API
 * @property components список компонентов экрана
 * @property scaffold скаффолд экрана
 */
data class Screen(
    val screenName: ScreenName,
    val description: String,
    val screenNavigationParams: Set<String>,
    val apis: Map<String, ShortApiRepresentation>,
    val components: List<Component>,
    val scaffold: Scaffold?,
)