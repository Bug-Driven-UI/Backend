package ru.hits.bdui.parser.visitor

import com.fasterxml.jackson.databind.JsonNode

object VariableValueExtractor {
    /**
     * @param keyList список параметров для получения из объекта. Пример: [outerField, innerField, text] (data.outerField.innerField.text)
     */
    fun extract(keyList: List<String>, data: JsonNode): Any? {
        val result = keyList.fold(data) { accumulate, key -> accumulate.path(key) }

        return result.getValue()
    }

    private fun JsonNode.getValue(): Any? =
        when {
            this.isMissingNode -> null
            this.isTextual -> this.textValue()
            this.isBoolean -> this.booleanValue()
            this.isNumber -> extractNumberValue(this)
            else -> throw ExtractionError("Неподдерживаемый тип значения в json: ${this.nodeType}")
        }

    private fun extractNumberValue(jsonNode: JsonNode): Number? =
        when {
            jsonNode.isLong -> jsonNode.longValue()
            jsonNode.isInt -> jsonNode.intValue()
            jsonNode.isShort -> jsonNode.intValue()
            jsonNode.isBigInteger -> jsonNode.bigIntegerValue()
            jsonNode.isFloatingPointNumber -> jsonNode.decimalValue()
            else -> jsonNode.numberValue()
        }

    private fun JsonNode.isMissingOrNull(): Boolean =
        this.isMissingNode || this.isNull
}

class ExtractionError(message: String) : Exception(message)