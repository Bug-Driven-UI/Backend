package ru.hits.bdui.expression

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.hits.bdui.core.expression.JSInterpreter

class JSInterpreterTest {
    private val objectMapper: ObjectMapper = jacksonObjectMapper()
    private val jsInterpreter: JSInterpreter = JSInterpreter(objectMapper)

    @BeforeEach
    fun setUp() {
        jsInterpreter.clearVariables()
    }

    @Test
    fun `GIVEN complex script and JSON variables WHEN evaluate THEN return evaluated result`() {
        jsInterpreter.setVariable("data", """
            {
              "items": [
                {
                  "id": "123",
                  "itemName": "Smartphone",
                  "quantity": "5",
                  "baseItemPrice": "50000",
                  "itemDiscountPercent": "5",
                  "imageUrl": "https://fsoinfgsg.jod/sief"
                },
                {
                  "id": "124",
                  "itemName": "Laptop",
                  "quantity": "2",
                  "baseItemPrice": "150000",
                  "itemDiscountPercent": "10",
                  "imageUrl": "https://example.com/laptop.jpg"
                }
              ]
            }
        """)
        val expected = """
            {
              "cartItems": [
                {
                  "itemId": "123",
                  "itemImageUrl": "https://fsoinfgsg.jod/sief",
                  "itemName": "Smartphone",
                  "itemTotalPrice": "237500.00",
                  "itemQuantity": 5,
                  "itemDiscount": "2500.00",
                  "itemDiscountPercent": "5.00%"
                },
                {
                  "itemId": "124",
                  "itemImageUrl": "https://example.com/laptop.jpg",
                  "itemName": "Laptop",
                  "itemTotalPrice": "270000.00",
                  "itemQuantity": 2,
                  "itemDiscount": "15000.00",
                  "itemDiscountPercent": "10.00%"
                }
              ],
              "totalCartItems": 7,
              "totalCartPrice": "507500.00"
            }
        """

        val mappingScript = """
            function transformCartData(inputJson) {
              var data = inputJson;
            
              var cartItems = Java.from(data.items).map(function (item) {
                var basePrice = parseFloat(item.baseItemPrice);
                var discountPercent = parseFloat(item.itemDiscountPercent);
                var quantity = parseInt(item.quantity, 10);
            
                return {
                  itemId: item.id,
                  itemImageUrl: item.imageUrl,
                  itemName: item.itemName,
                  itemTotalPrice: ((basePrice * (1 - discountPercent / 100)) * quantity).toFixed(2),
                  itemQuantity: quantity,
                  itemDiscount: (basePrice * (discountPercent / 100)).toFixed(2),
                  itemDiscountPercent: discountPercent.toFixed(2) + "%"
                };
              });
            
              var totalCartItems = cartItems.reduce(function (sum, item) {
                return sum + item.itemQuantity;
              }, 0);
              
              var totalCartPrice = cartItems.reduce(function (sum, item) {
                return sum + parseFloat(item.itemTotalPrice);
              }, 0).toFixed(2);
            
              return {
                cartItems: cartItems,
                totalCartItems: totalCartItems,
                totalCartPrice: totalCartPrice
              };
            }
            
            JSON.stringify(transformCartData(data));
        """

        val result = jsInterpreter.execute(mappingScript)

        assertEquals(result, objectMapper.writeValueAsString(objectMapper.readTree(expected)))
    }
}