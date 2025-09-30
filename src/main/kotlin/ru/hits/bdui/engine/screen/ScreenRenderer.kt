package ru.hits.bdui.engine.screen

import ru.hits.bdui.domain.engine.Interpreter
import ru.hits.bdui.domain.screen.Screen

class ScreenRenderer {

    /**
     * @param screen модель экрана (шаблон)
     * @param interpreter интерпретатор, у которого !ВАЖНО! уже должны быть установлены все необходимые значения переменных
     * (вызовы внешних API, параметры навигации)
     */
    fun renderScreen(
        screen: Screen,
        interpreter: Interpreter,
    ): Screen {
        return screen.copy(
            components = screen.components.map { component ->
                component.evaluator.evaluateComponent(component, interpreter)
            },
            scaffold = screen.scaffold?.let { scaffold ->
                scaffold.copy(
                    topBar = scaffold.topBar?.let { topBar ->
                        topBar.evaluator.evaluateComponent(topBar, interpreter)
                    },
                    bottomBar = scaffold.bottomBar?.let { bottomBar ->
                        bottomBar.evaluator.evaluateComponent(bottomBar, interpreter)
                    },
                )
            }
        )
    }
}
