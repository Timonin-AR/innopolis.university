package innopolis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Note {
    private Integer noteId;
    private String topic;
    private String text;
    private Timestamp dateAndTime;
}
