package ru.hits.bdui.expression

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.hits.bdui.core.expression.Interpreter
import ru.hits.bdui.core.expression.evaluateExpression
import kotlin.test.assertEquals

@SpringBootTest
class ExpressionInterpreterTest(@Autowired private val interpreter: Interpreter) {

    @BeforeEach
    fun setUp() {
        interpreter.clearVariables()
    }

    @Test
    fun `GIVEN expression with plain string variables WHEN evaluate THEN return evaluated expression`() {
        interpreter.setVariable("var1", "value1")
        interpreter.setVariable("var2", "value2")

        val expression = "This is \${var1} and \${var2}"
        val result = interpreter.evaluateExpression(expression)

        assertEquals("This is value1 and value2", result)
    }

    @Test
    fun `GIVEN expression with numbers WHEN evaluate THEN return evaluated expression`() {
        val expression = "Sum of 2 and 3 is \${2 + 3}"
        val result = interpreter.evaluateExpression(expression)

        assertEquals("Sum of 2 and 3 is 5", result)
    }

    @Test
    fun `GIVEN expression with escaped variable WHEN evaluate THEN return expression with escaped variable unchanged`() {
        val expression = "This is \\\${var1}"
        val result = interpreter.evaluateExpression(expression)

        assertEquals("This is \\\${var1}", result)
    }

    @Test
    fun `GIVEN expression with array variables WHEN evaluate THEN return evaluated expression`() {
        interpreter.setVariable("arr", """[1, 2, 3, 4, 5]""")

        val expression = "First element is \${arr[0]} and length is \${arr.length}"
        val result = interpreter.evaluateExpression(expression)

        assertEquals("First element is 1 and length is 5", result)
    }

    @Test
    fun `GIVEN expression with number variables WHEN evaluate THEN return evaluated expression`() {
        interpreter.setVariable("num1", "10")
        interpreter.setVariable("num2", "20")

        val expression = "Sum of \${num1} and \${num2} is \${(num1 + num2).toString()}"
        val result = interpreter.evaluateExpression(expression)

        assertEquals("Sum of 10 and 20 is 30", result)
    }

    @Test
    fun `GIVEN expression with boolean variables WHEN evaluate THEN return evaluated expression`() {
        interpreter.setVariable("bool1", "true")
        interpreter.setVariable("bool2", "true")

        val expression = "bool1 is \${bool1}, inverted bool2 is \${!bool2}, bool1 AND bool2 is \${bool1 && bool2}"
        val result = interpreter.evaluateExpression(expression)

        assertEquals("bool1 is true, inverted bool2 is false, bool1 AND bool2 is true", result)
    }

    @Test
    fun `GIVEN expression with JSON variables WHEN evaluate THEN return evaluated expression`() {
        interpreter.setVariable("data", """
            {
                "user": {
                    "name": "Alice",
                    "details": {
                        "age": 28,
                        "isMember": true
                    }
                },
                "scores": [85, 90, 78]
            }
        """)

        val expression = "Name: \${data.user.name}, Age: \${data.user.details.age}, Member: \${data.user.details.isMember}, First Score: \${data.scores[0]}"
        val result = interpreter.evaluateExpression(expression)

        assertEquals("Name: Alice, Age: 28, Member: true, First Score: 85", result)
    }
}