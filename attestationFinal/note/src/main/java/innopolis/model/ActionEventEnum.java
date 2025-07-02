package innopolis.model;

/**
 * События, которые можно выполнять над заметками.
 * ACTIVE - событие, которое помечает заметки как "Актуальные".
 * ARCHIVE - событие, которое помечает заметки как "Архивные".
 * DELETE - событие, которое удаляет переданные заметки по id
 */
public enum ActionEventEnum {
    ACTIVE, ARCHIVE, DELETE;
}
