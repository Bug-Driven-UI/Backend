package ru.hits.bdui.client.action.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ru.hits.bdui.client.action.ActionHandler
import ru.hits.bdui.client.action.controller.raw.actions.request.ActionRawRequest
import ru.hits.bdui.client.action.controller.raw.actions.request.HashNode
import ru.hits.bdui.client.action.controller.raw.actions.request.UpdateScreenActionRawRequest
import ru.hits.bdui.client.action.controller.raw.actions.response.ActionResponseRaw
import ru.hits.bdui.client.action.controller.raw.actions.response.UpdateInstructionRaw
import ru.hits.bdui.client.action.controller.raw.actions.response.UpdateMethodRaw
import ru.hits.bdui.client.action.controller.raw.actions.response.UpdateScreenActionResponseRaw
import ru.hits.bdui.client.action.controller.raw.actions.response.UpdateScreenResponsePayloadRaw
import ru.hits.bdui.client.screen.ScreenRenderService
import ru.hits.bdui.client.screen.controller.raw.RenderedScreenRawWrapper
import ru.hits.bdui.client.screen.controller.raw.utils.emerge
import ru.hits.bdui.client.screen.models.RenderScreenRequestModel
import ru.hits.bdui.common.models.client.raw.components.CompositeRawRendered
import ru.hits.bdui.common.models.client.raw.components.RenderedComponentRaw

@Component
class UpdateScreenActionHandler(
    private val screenRenderService: ScreenRenderService,
    private val objectMapper: ObjectMapper
) : ActionHandler {
    override val type: String = "updateScreen"

    private val log = LoggerFactory.getLogger(this::class.java)
    private val rootPath = "/"

    override fun execute(action: ActionRawRequest): Mono<ActionResponseRaw> {
        require(action is UpdateScreenActionRawRequest)

        val request = RenderScreenRequestModel(
            screenName = action.screenName,
            variables = action.screenNavigationParams.mapValues { entry -> objectMapper.valueToTree(entry.value) }
        )

        return screenRenderService.renderScreen(request)
            .map<ActionResponseRaw> { screen ->
                val rendered = RenderedScreenRawWrapper.emerge(screen)
                val roots: List<RenderedComponentRaw> = rendered.screen.components
                val oldIndex = prepareOldIndex(action)
                val deltas = diff(roots, oldIndex)

                UpdateScreenActionResponseRaw(
                    response = UpdateScreenResponsePayloadRaw(deltas)
                )
            }
            .doOnError { ex ->
                log.error("Ошибка обработки действия по обновлению экрана ${action.screenName}", ex)
            }
    }

    private fun prepareOldIndex(action: UpdateScreenActionRawRequest): OldIndex {
        val oldIndex = OldIndex()
        val stack = ArrayDeque<Pair<HashNode, String>>()
        for (i in action.hashes.indices.reversed()) {
            stack.addLast(action.hashes[i] to rootPath)
        }

        while (stack.isNotEmpty()) {
            val (current, parentPath) = stack.removeLast()
            val path = getPath(
                parentPath = parentPath,
                additionalPath = current.id
            )
            oldIndex.pathToNode[path] = current

            val children = current.children
            if (!children.isNullOrEmpty()) {
                for (i in children.indices.reversed()) {
                    stack.addLast(children[i] to path)
                }
            }
        }

        return oldIndex
    }

    private fun diff(
        roots: List<RenderedComponentRaw>,
        oldIndex: OldIndex
    ): List<UpdateInstructionRaw> {
        val deltas = mutableListOf<UpdateInstructionRaw>()

        val visitedNewPaths = mutableSetOf<String>()

        val coveredByParentChange = mutableSetOf<String>()

        val stack = ArrayDeque<Pair<RenderedComponentRaw, String>>()
        for (i in roots.indices.reversed()) {
            stack.addLast(roots[i] to rootPath)
        }

        while (stack.isNotEmpty()) {
            val (current, parentPath) = stack.removeLast()
            val path = getPath(parentPath, current.base.id)
            visitedNewPaths += path

            val oldNode = oldIndex.pathToNode[path]

            when {
                // Узла раньше не было — вставляем целое поддерево и не спускаемся
                oldNode == null -> {
                    deltas += UpdateInstructionRaw(
                        target = path,
                        method = UpdateMethodRaw.INSERT,
                        content = current
                    )
                    coveredByParentChange += path
                    continue
                }

                // Узел был, но контент поменялся — обновляем все поддерево и не трогаем детей
                oldNode.hash != current.base.hash -> {
                    deltas += UpdateInstructionRaw(
                        target = path,
                        method = UpdateMethodRaw.UPDATE,
                        content = current
                    )
                    coveredByParentChange += path
                    continue
                }

                // Хэши совпали — идем проверять детей
                else -> {
                    if (current is CompositeRawRendered) {
                        for (i in current.children.indices.reversed()) {
                            stack.addLast(current.children[i] to path)
                        }
                    }
                }
            }
        }

        // Удаления: всё, что было в старом, но не встретилось в новом,
        // и при этом не является ребенком узла, который мы уже добавили или обновили.
        oldIndex.pathToNode.keys.forEach { oldPath ->
            val shadowedByParent = coveredByParentChange.any { cover -> equalOrDescendant(oldPath, cover) }
            if (!shadowedByParent && oldPath !in visitedNewPaths) {
                deltas += UpdateInstructionRaw(
                    target = oldPath,
                    method = UpdateMethodRaw.DELETE,
                    content = null
                )
            }
        }

        return deltas
    }

    private fun equalOrDescendant(oldPath: String, cover: String): Boolean =
        oldPath == cover || oldPath.startsWith("$cover/")

    private data class NewNode(
        val id: String,
        val hash: String,
        val path: String,
    )

    private class NewIndex {
        val pathToNode = linkedMapOf<String, NewNode>()
        val components = linkedMapOf<String, RenderedComponentRaw>()
    }

    private fun getPath(parentPath: String, additionalPath: String): String =
        if (parentPath != rootPath) "$parentPath/$additionalPath"
        else "$rootPath$additionalPath"

    private class OldIndex {
        val pathToNode = linkedMapOf<String, HashNode>()
    }
}