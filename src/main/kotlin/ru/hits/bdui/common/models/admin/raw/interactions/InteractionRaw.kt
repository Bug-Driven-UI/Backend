package ru.hits.bdui.common.models.admin.raw.interactions

import com.fasterxml.jackson.annotation.JsonProperty
import ru.hits.bdui.common.models.admin.raw.interactions.actions.ActionRaw


data class InteractionRaw(
    val type: InteractionTypeRaw,
    val actions: List<ActionRaw>
)

enum class InteractionTypeRaw {
    @JsonProperty("onClick")
    ON_CLICK,

    @JsonProperty("onShow")
    ON_SHOW
}