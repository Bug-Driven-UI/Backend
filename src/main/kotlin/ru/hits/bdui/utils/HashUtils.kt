package ru.hits.bdui.utils

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.BooleanNode
import com.fasterxml.jackson.databind.node.NullNode
import com.fasterxml.jackson.databind.node.NumericNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.node.TextNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.hits.bdui.domain.screen.components.Component
import java.security.MessageDigest
import java.util.Base64

object DomainComponentHasher {
    private val defaultIgnoredFields = setOf("children")
    private val mapper: ObjectMapper = jacksonObjectMapper()
        .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
        .setSerializationInclusion(JsonInclude.Include.ALWAYS)

    fun calculate(component: Component, ignoreFields: Set<String> = defaultIgnoredFields): String =
        calculateAny(component, ignoreFields)

    fun calculateAny(value: Any?, ignoreFields: Set<String> = emptySet()): String {
        val original = mapper.valueToTree<JsonNode>(value)
        val pruned = if (ignoreFields.isEmpty()) original else prune(original, ignoreFields)
        val canonical = canonicalize(pruned)
        return sha256Base64Url(canonical.toByteArray())
    }

    private fun prune(node: JsonNode, ignoreFields: Set<String>): JsonNode =
        when (node) {
            is ObjectNode -> {
                val copy = mapper.nodeFactory.objectNode()
                val names = buildList { node.fieldNames().forEachRemaining { add(it) } }
                for (name in names.sorted()) {
                    if (name in ignoreFields) continue
                    copy.set<JsonNode>(name, prune(node.get(name), ignoreFields))
                }
                copy
            }

            is ArrayNode -> {
                val copy = mapper.nodeFactory.arrayNode()
                node.forEach { copy.add(prune(it, ignoreFields)) }
                copy
            }

            else -> node
        }

    private fun sha256Base64Url(bytes: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest)
    }

    /**
     * Каноническое строковое представление JSON-дерева:
     * - Object: { "k1":canon(v1),"k2":canon(v2) } с лексикографической сортировкой имён полей
     * - Array:  [ canon(e1),canon(e2),... ] в исходном порядке
     * - Примитивы: стабильные строковые формы с экранированием
     */
    private fun canonicalize(node: JsonNode?): String =
        when (node) {
            null -> "null"
            is ObjectNode -> {
                val keys = buildList {
                    node.fieldNames().forEachRemaining { add(it) }
                }.sorted()
                buildString {
                    append('{')
                    keys.forEachIndexed { i, k ->
                        if (i > 0) append(',')
                        append(quote(k)).append(':')
                        append(canonicalize(node.get(k)))
                    }
                    append('}')
                }
            }

            is ArrayNode -> buildString {
                append('[')
                node.forEachIndexed { i, child ->
                    if (i > 0) append(',')
                    append(canonicalize(child))
                }
                append(']')
            }

            is TextNode -> quote(node.textValue())
            is NumericNode -> node.numberValue().toString()
            is BooleanNode -> node.booleanValue().toString()
            is NullNode -> "null"
            else -> quote(node.asText())
        }

    /** Минимальное экранирование строк (как в JSON). */
    private fun quote(s: String): String {
        val sb = StringBuilder(s.length + 2)
        sb.append('"')
        s.forEach { ch ->
            when (ch) {
                '\\' -> sb.append("\\\\")
                '"' -> sb.append("\\\"")
                '\b' -> sb.append("\\b")
                '\u000C' -> sb.append("\\f")
                '\n' -> sb.append("\\n")
                '\r' -> sb.append("\\r")
                '\t' -> sb.append("\\t")
                else -> if (ch < ' ') sb.append("\\u%04x".format(ch.code)) else sb.append(ch)
            }
        }
        sb.append('"')
        return sb.toString()
    }
}