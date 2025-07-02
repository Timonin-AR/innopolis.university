package innopolis.service;

import innopolis.model.NoteEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final WorkerService workerService;

    @KafkaListener(topics = "note-events", groupId = "note-worker-group")
    public void listenArchiveEvent(ConsumerRecord<String, NoteEventDto> record) {
        var noteIds = record.value().getNoteIds();
        var action = record.value().getAction();
        log.info("Начал работу потребитель кафки по событию: {}", action);
        switch (action) {
            case ACTIVE -> workerService.sendNoteToActual(noteIds);
            case ARCHIVE -> workerService.sendNoteToArchive(noteIds);
            case DELETE -> workerService.deleteArchiveNote(noteIds);
        }
    }

}
