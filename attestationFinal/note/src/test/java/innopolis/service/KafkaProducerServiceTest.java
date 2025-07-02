package innopolis.service;

import innopolis.model.ActionEventEnum;
import innopolis.model.NoteEventDto;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<String, NoteEventDto> kafkaTemplate;

    @InjectMocks
    private KafkaProducerService kafkaProducerService;

    @Test
    void sendEventWithNoteIds_ShouldSendCorrectMessageToKafka() throws Exception {
        var eventDto = new NoteEventDto(List.of(1L, 2L), ActionEventEnum.DELETE);
        CompletableFuture<SendResult<String, NoteEventDto>> future = CompletableFuture.completedFuture(new SendResult<>(new ProducerRecord<>("note-events", eventDto), null));

        when(kafkaTemplate.send(eq("note-events"), eq(eventDto))).thenReturn(future);

        kafkaProducerService.sendEventWithNoteIds(eventDto);

        var topicCaptor = ArgumentCaptor.forClass(String.class);
        var messageCaptor = ArgumentCaptor.forClass(NoteEventDto.class);

        verify(kafkaTemplate).send(topicCaptor.capture(), messageCaptor.capture());
        assertEquals("note-events", topicCaptor.getValue());
        assertEquals(eventDto, messageCaptor.getValue());
    }


    @Test
    void getMessagesTopic_ShouldReturnCorrectTopic() {
        var topic = kafkaProducerService.getMessagesTopic();
        assertEquals("note-events", topic.name());
    }
}
