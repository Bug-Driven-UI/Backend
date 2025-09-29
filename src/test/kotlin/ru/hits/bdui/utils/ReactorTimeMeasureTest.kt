package ru.hits.bdui.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.kotlin.test.test
import java.time.Duration

class ReactorTimeMeasureTest {

    @Test
    @DisplayName("Измерение времени выполнения учитывает и не влияет на сигнал onNext")
    fun onNextShouldNotAffectMono() {
        var duration: Duration? = null

        val testMono = Mono.fromCallable { "test" }
            .doOnNextWithMeasure { evalDuration, _ ->
                duration = evalDuration
            }

        testMono
            .test()
            .expectNext("test")
            .verifyComplete()

        assertThat(duration).isNotNull
    }
}