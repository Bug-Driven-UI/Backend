package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.ComponentId
import ru.hits.bdui.domain.screen.interactions.Interaction
import ru.hits.bdui.domain.screen.properties.Insets
import ru.hits.bdui.domain.screen.properties.Size

sealed interface Component {
    val id: ComponentId
    val type: String
    val hash: String
    val interactions: List<Interaction>
    val insets: Insets
    val width: Size
    val height: Size
}