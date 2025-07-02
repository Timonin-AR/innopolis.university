package innopolis.service;

import innopolis.model.NoteEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Класс для отправки сообщений в кафку
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    @Bean
    public NewTopic getMessagesTopic() {
        return TopicBuilder.name("note-events").build();
    }

    private final KafkaTemplate<String, NoteEventDto> kafkaTemplate;


    public void sendEventWithNoteIds(NoteEventDto noteEventDto) {
        kafkaTemplate.send("note-events", noteEventDto);
        log.info("Отправляю событие {} в кафку с id заметок: {} в топик note-events", noteEventDto.getAction(), noteEventDto.getNoteIds());
    }
}
