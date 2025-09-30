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

@Component
class RepeatedIdChecker {
    /**
     * Проверяет наличие повторяющихся id.
     *
     * @return множество повторяющихся (или проблемных) id
     */
    fun checkIds(root: ComponentRaw): Set<String> {
        val repeatedIds = mutableSetOf<String>()
        val seen = mutableSetOf<String>()

        fun register(id: String) {
            if (!seen.add(id)) {
                repeatedIds += id
            }
        }

        fun walk(component: ComponentRaw) {
            when (component) {
                is BoxRaw -> {
                    register(component.base.id)
                    component.children.forEach(::walk)
                }

                is ColumnRaw -> {
                    register(component.base.id)
                    component.children.forEach(::walk)
                }

                is RowRaw -> {
                    register(component.base.id)
                    component.children.forEach(::walk)
                }

                //TODO(Необходимо добавить проверку на наличие повторяющегося id в шаблоне)
                is DynamicColumnRaw -> {
                    register(component.base.id)
                }

                //TODO(Необходимо добавить проверку на наличие повторяющегося id в шаблоне)
                is DynamicRowRaw -> {
                    register(component.base.id)
                }

                is ButtonRaw -> register(component.base.id)
                is ImageRaw -> register(component.base.id)
                is InputRaw -> register(component.base.id)
                is ProgressBarRaw -> register(component.base.id)
                is SpacerRaw -> register(component.base.id)
                is SwitchRaw -> register(component.base.id)
                is TextRaw -> register(component.base.id)

                is StatefulComponentRaw -> {
                    register(component.base.id)
                    component.states.forEach { state ->
                        walk(state.component)
                    }
                }
            }
        }

        walk(root)
        return repeatedIds
    }
}