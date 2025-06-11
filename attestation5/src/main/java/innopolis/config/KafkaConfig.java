package innopolis.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import innopolis.dto.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {


    private final ObjectMapper mapper;
    private final KafkaProperties kafkaProperties;

    @Bean
    public NewTopic getMessagesTopic() {
        return TopicBuilder.name("order").build();
    }


    @Bean
    public ReceiverOptions<String, KafkaMessage> receiverOptions() {
        return ReceiverOptions.<String, KafkaMessage>create(kafkaProperties.buildConsumerProperties(null))
                .subscription(List.of("order"))
                .withKeyDeserializer(new StringDeserializer())
                .withValueDeserializer(new JsonDeserializer<>(KafkaMessage.class, mapper))
                .consumerProperty(JsonDeserializer.TRUSTED_PACKAGES, "*");
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, KafkaMessage> reactiveKafkaConsumerTemplate() {
        return new ReactiveKafkaConsumerTemplate<>(receiverOptions());
    }

    @Bean
    public SenderOptions<String, KafkaMessage> senderOptions() {
        return SenderOptions.<String, KafkaMessage>create(kafkaProperties.buildConsumerProperties())
                .producerProperty(JsonSerializer.ADD_TYPE_INFO_HEADERS, false)
                .withKeySerializer(new StringSerializer())
                .withValueSerializer(new JsonSerializer<>(mapper));
    }

    @Bean
    public ReactiveKafkaProducerTemplate<String, KafkaMessage> reactiveKafkaProducerTemplate() {
        return new ReactiveKafkaProducerTemplate<>(senderOptions());
    }


}
