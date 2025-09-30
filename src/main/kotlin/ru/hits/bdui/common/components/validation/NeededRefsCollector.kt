package ru.hits.bdui.common.components.validation

import org.springframework.stereotype.Component
import ru.hits.bdui.common.models.admin.raw.components.BoxRaw
import ru.hits.bdui.common.models.admin.raw.components.ButtonRaw
import ru.hits.bdui.common.models.admin.raw.components.ColumnRaw
import ru.hits.bdui.common.models.admin.raw.components.ComponentRaw
import ru.hits.bdui.common.models.admin.raw.components.DynamicColumnRaw
import ru.hits.bdui.common.models.admin.raw.components.DynamicRowRaw
import ru.hits.bdui.common.models.admin.raw.components.ImageRaw
import ru.hits.bdui.common.models.admin.raw.components.InputRaw
import ru.hits.bdui.common.models.admin.raw.components.ProgressBarRaw
import ru.hits.bdui.common.models.admin.raw.components.RowRaw
import ru.hits.bdui.common.models.admin.raw.components.SpacerRaw
import ru.hits.bdui.common.models.admin.raw.components.StatefulComponentRaw
import ru.hits.bdui.common.models.admin.raw.components.SwitchRaw
import ru.hits.bdui.common.models.admin.raw.components.TextRaw
import ru.hits.bdui.common.models.admin.raw.styles.text.TextWithStyleRaw

data class NeededRefs(
    val colorTokens: Set<String>,
    val textTokens: Set<String>,
    val templateNames: Set<String>,
    val duplicatedIds: Set<String> = emptySet()
)

@Component
class NeededRefsCollector {
    fun collect(component: ComponentRaw): NeededRefs =
        collect(listOf(component))

    fun collect(components: List<ComponentRaw>): NeededRefs {
        if (components.isEmpty()) {
            return NeededRefs(emptySet(), emptySet(), emptySet(), emptySet())
        }

        val colors = mutableSetOf<String>()
        val texts = mutableSetOf<String>()
        val templates = mutableSetOf<String>()
        val seenIds = mutableSetOf<String>()
        val duplicatedIds = mutableSetOf<String>()

        fun extractTokensFromTextWithStyle(textWithStyle: TextWithStyleRaw) {
            texts += textWithStyle.textStyle.token
            colors += textWithStyle.colorStyle.token
        }

        val stack = ArrayDeque<ComponentRaw>()
        for (c in components) stack.addLast(c)

        while (stack.isNotEmpty()) {
            val component = stack.removeLast()

            val id = component.base.id
            if (!seenIds.add(id)) duplicatedIds += id

            component.base.backgroundColor?.let { colors += it.token }
            component.base.border?.color?.let { colors += it.token }

            when (component) {
                is TextRaw -> extractTokensFromTextWithStyle(component.textWithStyle)
                is ButtonRaw -> extractTokensFromTextWithStyle(component.textWithStyle)

                is InputRaw -> {
                    extractTokensFromTextWithStyle(component.textWithStyle)
                    component.hint?.let { extractTokensFromTextWithStyle(it.textWithStyle) }
                    component.placeholder?.let { extractTokensFromTextWithStyle(it.textWithStyle) }
                    val badge = component.rightIcon?.badge
                    if (badge is ImageRaw.BadgeRaw.BadgeWithTextRaw) {
                        extractTokensFromTextWithStyle(badge.textWithStyle)
                    }
                }

                is ImageRaw -> {
                    val badge = component.badge
                    if (badge is ImageRaw.BadgeRaw.BadgeWithTextRaw) {
                        extractTokensFromTextWithStyle(badge.textWithStyle)
                    }
                }

                is ColumnRaw -> for (child in component.children) stack.addLast(child)
                is RowRaw -> for (child in component.children) stack.addLast(child)
                is BoxRaw -> for (child in component.children) stack.addLast(child)
                is StatefulComponentRaw -> for (st in component.states) stack.addLast(st.component)

                is DynamicColumnRaw -> templates += component.itemTemplateName
                is DynamicRowRaw -> templates += component.itemTemplateName

                is SpacerRaw, is ProgressBarRaw, is SwitchRaw -> {}
            }
        }

        return NeededRefs(
            colorTokens = colors,
            textTokens = texts,
            templateNames = templates,
            duplicatedIds = duplicatedIds
        )
    }
}
