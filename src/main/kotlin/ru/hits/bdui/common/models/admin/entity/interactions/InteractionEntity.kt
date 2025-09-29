package ru.hits.bdui.common.models.admin.entity.interactions

import com.fasterxml.jackson.annotation.JsonProperty
import ru.hits.bdui.common.models.admin.entity.interactions.actions.ActionEntity


data class InteractionEntity(
    val type: InteractionTypeEntity,
    val actions: List<ActionEntity>
)

enum class InteractionTypeEntity {
    @JsonProperty("onClick")
    ON_CLICK,

    @JsonProperty("onShow")
    ON_SHOW
}