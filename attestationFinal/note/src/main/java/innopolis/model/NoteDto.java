package innopolis.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для передачи данных о заметках")
public class NoteDto {
    @Schema(description = "Уникальный идентификатор заметки. Поле не обязательное только при создании")
    private Long noteId;
    @Schema(description = "Наименование заголовка заметки. Поле обязательное при создании")
    private String title;
    @Schema(description = "Описание заметки. Поле опциональное")
    private String description;
    @Schema(description = """
            Дата создания заявки. Поле опциональное.
            Если не заполнить, то будет установлена сегодняшняя дата.
            Можно указать планируемую дату, в которую заметка станет "Актуальной".""")
    private LocalDate createDate;
}
