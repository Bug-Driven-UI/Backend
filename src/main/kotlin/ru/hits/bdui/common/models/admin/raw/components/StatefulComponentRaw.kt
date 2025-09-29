package ru.hits.bdui.common.models.admin.raw.components

import ru.hits.bdui.common.models.admin.raw.components.additional.BorderRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.ShapeRaw
import ru.hits.bdui.common.models.admin.raw.components.properties.InsetsRaw
import ru.hits.bdui.common.models.admin.raw.components.properties.SizeRaw
import ru.hits.bdui.common.models.admin.raw.interactions.InteractionRaw
import ru.hits.bdui.common.models.admin.raw.styles.color.ColorStyleRaw

/**
 * Обертка для компонентов, которые зависят от конкретных условий
 */
data class StatefulComponentRaw(
    override val id: String,
    override val interactions: List<InteractionRaw> = emptyList(),
    override val margins: InsetsRaw?,
    override val paddings: InsetsRaw?,
    override val width: SizeRaw,
    override val height: SizeRaw,
    override val backgroundColor: ColorStyleRaw?,
    val states: List<StateDefinitionRaw>,
    override val border: BorderRaw?,
    override val shape: ShapeRaw?
) : ComponentRaw {
    override val type: String = "stateful"
}

/**
 * @property condition условие, которое должно выполниться для использования объекта из данного состояния
 */
data class StateDefinitionRaw(
    val condition: String,
    val component: ComponentRaw
)
