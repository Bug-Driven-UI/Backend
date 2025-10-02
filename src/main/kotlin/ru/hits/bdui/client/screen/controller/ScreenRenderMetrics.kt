package ru.hits.bdui.client.screen.controller

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class ScreenRenderMetrics(
    private val meterRegistry: MeterRegistry,
) {
    private val slaList = listOf<Long>(
        1, 5, 10, 25, 50, 100, 200, 300, 400, 500, 600, 700, 800, 900,
        1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000,
        15000, 20000
    )
        .map(Duration::ofMillis)
        .toTypedArray()

    fun incrementMetrics(screenName: String, duration: Duration) {
        increment(screenName, duration)
        recordLatency(screenName, duration)
    }

    private fun increment(screenName: String, duration: Duration) {
        Counter
            .builder("screen.render.request")
            .tag("screenName", screenName)
            .register(meterRegistry)
            .increment()
    }

    private fun recordLatency(screenName: String, duration: Duration) {
        Timer
            .builder("screen.render.request.duration")
            .tag("screenName", screenName)
            .sla(*slaList)
            .register(meterRegistry)
            .record(duration)
    }
}