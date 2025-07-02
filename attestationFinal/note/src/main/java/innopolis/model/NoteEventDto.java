package innopolis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Объект для отправки событий в сервис worker через кафку
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteEventDto {
    private List<Long> noteIds;
    private ActionEventEnum action;
}
