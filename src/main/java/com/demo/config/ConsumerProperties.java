package com.demo.config;

import com.demo.model.Product;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ConsumerProperties {
    @Bean
    public Map<String, Object> consumerConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return config;
    }

    private JsonDeserializer<Product> deserializer() {
        JsonDeserializer<Product> deserializer = new JsonDeserializer<>();
        deserializer.addTrustedPackages("*");
        deserializer.setRemoveTypeHeaders(true);

        return deserializer;
    }

    @Bean
    public ConsumerFactory<String, Product> consumerFactory() {
        return new DefaultKafkaConsumerFactory<String, Product>(consumerConfiguration(), new StringDeserializer(), deserializer());
    }



    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Product> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Product> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}
