package ru.hits.bdui.engine.screen

import ru.hits.bdui.domain.screen.Screen
import ru.hits.bdui.engine.Interpreter

@org.springframework.stereotype.Component
class ScreenRenderer(
    private val gateway: ComponentEvaluationGateway
) {
    /**
     * @param screen модель экрана (шаблон)
     * @param interpreter интерпретатор, у которого !ВАЖНО! уже должны быть установлены все необходимые значения переменных
     * (вызовы внешних API, параметры навигации)
     */
    fun renderScreen(
        screen: Screen,
        interpreter: Interpreter,
    ): Screen =
        screen.copy(
            components = screen.components.map { component ->
                gateway.evaluate(component, interpreter)
            },
            scaffold = screen.scaffold?.let { scaffold ->
                scaffold.copy(
                    topBar = scaffold.topBar?.let { topBar ->
                        gateway.evaluate(topBar, interpreter)
                    },
                    bottomBar = scaffold.bottomBar?.let { bottomBar ->
                        gateway.evaluate(bottomBar, interpreter)
                    },
                )
            }
        )
}
