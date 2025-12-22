package ru.hits.bdui.outbox.scheduler

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.KafkaException
import org.apache.kafka.common.errors.AuthorizationException
import org.apache.kafka.common.errors.OutOfOrderSequenceException
import org.apache.kafka.common.errors.ProducerFencedException
import org.springframework.stereotype.Component
import ru.hits.bdui.outbox.event.OutboxEvent

@Component
class TransactionalKafkaEventSender(
    private val producer: KafkaProducer<String, String>,
) {
    fun send(topic: String, events: List<OutboxEvent>) {
        try {
            producer.beginTransaction()

            events.forEach { event ->
                val record = ProducerRecord(topic, event.id.toString(), event.payload)
                producer.send(record)
            }

            producer.commitTransaction()

        } catch (e: ProducerFencedException) {
            producer.close()
        } catch (e: OutOfOrderSequenceException) {
            producer.close()
        } catch (e: AuthorizationException) {
            producer.close()
        } catch (e: KafkaException) {
            producer.abortTransaction()
        }
    }
}