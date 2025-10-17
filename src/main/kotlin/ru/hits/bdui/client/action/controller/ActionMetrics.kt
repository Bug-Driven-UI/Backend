package ru.hits.bdui.client.action.controller

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class ActionMetrics(
    private val meterRegistry: MeterRegistry,
) {
    private val slaList = listOf<Long>(
        1, 5, 10, 25, 50, 100, 200, 300, 400, 500, 600, 700, 800, 900,
        1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000,
        15000, 20000
    )
        .map(Duration::ofMillis)
        .toTypedArray()

    fun incrementMetrics(actionName: String, duration: Duration) {
        increment(actionName)
        recordLatency(actionName, duration)
    }

    private fun increment(actionName: String) {
        Counter
            .builder("action.request")
            .tag("actionName", actionName)
            .register(meterRegistry)
            .increment()
    }

    private fun recordLatency(actionName: String, duration: Duration) {
        Timer
            .builder("action.request.duration")
            .tag("actionName", actionName)
            .sla(*slaList)
            .register(meterRegistry)
            .record(duration)
    }
}