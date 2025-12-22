package ru.hits.bdui.config

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(KafkaProperties::class)
class KafkaConfiguration {
    @Bean
    fun transactionalProducer(kafkaProperties: KafkaProperties): KafkaProducer<String, String> {
        val properties = mutableMapOf<String, Any>()
        properties[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.bootstrapServers
        properties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        properties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        properties[ProducerConfig.TRANSACTIONAL_ID_CONFIG] = "kafka_tx-1"
        properties[ProducerConfig.LINGER_MS_CONFIG] = "200"
        properties[ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG] = true

        return KafkaProducer<String, String>(properties)
            .also { it.initTransactions() }
    }
}

@ConfigurationProperties(value = "kafka")
class KafkaProperties(
    val bootstrapServers: List<String>,
)