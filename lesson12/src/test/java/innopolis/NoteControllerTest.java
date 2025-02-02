package innopolis;

import com.fasterxml.jackson.databind.ObjectMapper;
import innopolis.controller.NoteController;
import innopolis.entity.Note;
import innopolis.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NoteControllerTest {

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getNoteTest() throws Exception {
        var one = 1;
        var topic = "Тема";
        var text = "Текст";
        when(noteService.getNoteById(one))
                .thenReturn(new Note(one, topic, text, Timestamp.valueOf(LocalDateTime.now())));
        mockMvc.perform(get("/api/v1/{id}", one))
                .andExpect(status().isOk())
                .andExpect(jsonPath("noteId").value(one))
                .andExpect(jsonPath("topic").value(topic))
                .andExpect(jsonPath("text").value(text));
        verify(noteService, times(one)).getNoteById(one);
    }

    @Test
    void createNoteTest() throws Exception {
        var note = new Note(null, "Тема", "Текст", null);
        mockMvc.perform(post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(0));
        verify(noteService, times(1)).createNote(new Note());
    }

    @Test
    void deleteNoteTest() throws Exception {
        mockMvc.perform(delete("/api/v1/{id}", 1))
                .andExpect(status().isOk());
        verify(noteService, times(1)).deleteNote(1);
    }

    @Test
    void updateNoteTest() throws Exception {
        var note = new Note(1, "Тема", "Текст", null);
        mockMvc.perform(put("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk());
        verify(noteService, times(1)).updateNote(note);
    }


}
