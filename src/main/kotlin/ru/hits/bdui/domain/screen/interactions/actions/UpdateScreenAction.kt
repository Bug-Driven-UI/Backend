package ru.hits.bdui.domain.screen.interactions.actions

import ru.hits.bdui.domain.ScreenName

data class UpdateScreenAction(
    val screenName: ScreenName,
    val screenNavigationParams: Map<String, String>
) : Action {
    override val type: String = "updateScreen"
}