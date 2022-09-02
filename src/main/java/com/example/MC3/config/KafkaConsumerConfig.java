package com.example.MC3.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import java.util.HashMap;

@EnableKafka
@Configuration
public class KafkaConsumerConfig
{

    @Value(value = "${my.kafka.bootstrap.address}")
    private String bootstrapAddress;

    @Value(value = "${my.kafka.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, Object> consumerFactory()
    {
        var props = new HashMap<String, Object>();
        props.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress );
        props.put( ConsumerConfig.GROUP_ID_CONFIG, groupId );
        props.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class );
        props.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class );
        props.put( ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 0 );
        return new DefaultKafkaConsumerFactory<>( props );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory()
    {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
        factory.setConsumerFactory( consumerFactory() );
        return factory;
    }
}
