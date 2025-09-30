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
    val templateNames: Set<String>
)

@Component
class NeededRefsCollector {
    fun collect(root: ComponentRaw): NeededRefs {
        val colors = mutableSetOf<String>()
        val texts = mutableSetOf<String>()
        val templates = mutableSetOf<String>()

        fun extractTokensFromTextWithStyle(textWithStyle: TextWithStyleRaw) {
            texts += textWithStyle.textStyle.token
            colors += textWithStyle.colorStyle.token
        }

        fun walk(component: ComponentRaw) {
            when (component) {
                is TextRaw -> extractTokensFromTextWithStyle(component.textWithStyle)
                is ButtonRaw -> extractTokensFromTextWithStyle(component.textWithStyle)
                is InputRaw -> {
                    extractTokensFromTextWithStyle(component.textWithStyle)
                    component.hint?.let { extractTokensFromTextWithStyle(it.textWithStyle) }
                    component.placeholder?.let { extractTokensFromTextWithStyle(it.textWithStyle) }
                    component.rightIcon?.badge?.let {
                        when (it) {
                            is ImageRaw.BadgeRaw.BadgeWithTextRaw -> extractTokensFromTextWithStyle(it.textWithStyle)
                            is ImageRaw.BadgeRaw.BadgeWithImageRaw -> {}
                        }
                    }
                }

                is ImageRaw -> {
                    component.badge?.let {
                        when (it) {
                            is ImageRaw.BadgeRaw.BadgeWithTextRaw -> extractTokensFromTextWithStyle(it.textWithStyle)
                            is ImageRaw.BadgeRaw.BadgeWithImageRaw -> {}
                        }
                    }
                }

                is ColumnRaw -> component.children.forEach(::walk)
                is RowRaw -> component.children.forEach(::walk)
                is BoxRaw -> component.children.forEach(::walk)
                is StatefulComponentRaw -> component.states.forEach { walk(it.component) }
                is DynamicColumnRaw -> templates += component.itemTemplateName
                is DynamicRowRaw -> templates += component.itemTemplateName
                is SpacerRaw, is ProgressBarRaw, is SwitchRaw -> {}
            }
            component.base.backgroundColor?.let { colors += it.token }
            component.base.border?.color?.let { colors += it.token }
        }

        walk(root)
        return NeededRefs(colors, texts, templates)
    }
}
