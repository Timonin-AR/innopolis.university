package innopolis.service;

import innopolis.model.ActionEventEnum;
import innopolis.model.NoteEventDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class KafkaConsumerServiceTest {

    @Mock
    private WorkerService workerService;


    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    @Test
    void listenArchiveEvent_ShouldHandleActiveAction() {
        var event = new NoteEventDto(List.of(1L, 2L), ActionEventEnum.ACTIVE);
        ConsumerRecord<String, NoteEventDto> record = new ConsumerRecord<>("note-events", 0, 0, "key", event);
        kafkaConsumerService.listenArchiveEvent(record);
        verify(workerService).sendNoteToActual(List.of(1L, 2L));
    }

    @Test
    void listenArchiveEvent_ShouldHandleArchiveAction() {
        var event = new NoteEventDto(List.of(3L, 4L), ActionEventEnum.ARCHIVE);
        ConsumerRecord<String, NoteEventDto> record = new ConsumerRecord<>("note-events", 0, 0, "key", event);
        kafkaConsumerService.listenArchiveEvent(record);
        verify(workerService).sendNoteToArchive(List.of(3L, 4L));
    }

    @Test
    void listenArchiveEvent_ShouldHandleDeleteAction() {
        var event = new NoteEventDto(List.of(5L), ActionEventEnum.DELETE);
        ConsumerRecord<String, NoteEventDto> record = new ConsumerRecord<>("note-events", 0, 0, "key", event);
        kafkaConsumerService.listenArchiveEvent(record);
        verify(workerService).deleteArchiveNote(List.of(5L));
    }


    @Test
    void listenArchiveEvent_ShouldHandleEmptyNoteIds() {
        var event = new NoteEventDto(List.of(), ActionEventEnum.ACTIVE);
        ConsumerRecord<String, NoteEventDto> record = new ConsumerRecord<>("note-events", 0, 0, "key", event);
        kafkaConsumerService.listenArchiveEvent(record);
        verify(workerService).sendNoteToActual(List.of());
    }
}
