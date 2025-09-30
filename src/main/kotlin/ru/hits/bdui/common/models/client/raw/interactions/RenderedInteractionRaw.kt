package ru.hits.bdui.common.models.client.raw.interactions

import com.fasterxml.jackson.annotation.JsonProperty
import ru.hits.bdui.common.models.client.raw.interactions.actions.RenderedActionRaw


data class RenderedInteractionRaw(
    val type: RenderedInteractionTypeRaw,
    val actions: List<RenderedActionRaw>
)

enum class RenderedInteractionTypeRaw {
    @JsonProperty("onClick")
    ON_CLICK,

    @JsonProperty("onShow")
    ON_SHOW
}
