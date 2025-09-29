package ru.hits.bdui.common.models.admin.entity.components

import ru.hits.bdui.common.models.admin.entity.components.additional.BorderEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.ShapeEntity
import ru.hits.bdui.common.models.admin.entity.components.properties.InsetsEntity
import ru.hits.bdui.common.models.admin.entity.components.properties.SizeEntity
import ru.hits.bdui.common.models.admin.entity.interactions.InteractionEntity
import ru.hits.bdui.common.models.admin.entity.styles.color.ColorStyleEntity

/**
 * Обертка для компонентов, которые зависят от конкретных условий
 */
data class StatefulComponentEntity(
    override val id: String,
    override val interactions: List<InteractionEntity> = emptyList(),
    override val margins: InsetsEntity?,
    override val paddings: InsetsEntity?,
    override val width: SizeEntity,
    override val height: SizeEntity,
    override val backgroundColor: ColorStyleEntity?,
    val states: List<StateDefinitionEntity>,
    override val border: BorderEntity?,
    override val shape: ShapeEntity?
) : ComponentEntity {
    override val type: String = "stateful"
}

/**
 * @property condition условие, которое должно выполниться для использования объекта из данного состояния
 */
data class StateDefinitionEntity(
    val condition: String,
    val component: ComponentEntity
)
