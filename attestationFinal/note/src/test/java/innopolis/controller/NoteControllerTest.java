package innopolis.controller;


import innopolis.model.NoteDto;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(noteController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void createNote_ShouldReturnCreatedNoteId() throws Exception {
        var responseDto = new NoteDto();
        responseDto.setNoteId(1L);
        when(noteService.createNote(any(NoteDto.class), anyLong())).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/note/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Note\"}")
                        .requestAttr("userId", 123L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1L));
    }

    @Test
    void updateNote_ShouldReturnUpdatedNote() throws Exception {
        var requestDto = new NoteDto();
        requestDto.setNoteId(1L);
        requestDto.setTitle("Updated Note");
        when(noteService.updateNote(any(NoteDto.class), anyLong())).thenReturn(requestDto);

        mockMvc.perform(post("/api/v1/note/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"noteId\":1,\"title\":\"Updated Note\"}")
                        .requestAttr("userId", 123L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.noteId").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Note"));
    }

    @Test
    void getAllNotes_ShouldReturnNotesList() throws Exception {
        var note1 = new NoteDto();
        note1.setNoteId(1L);
        var note2 = new NoteDto();
        note2.setNoteId(2L);
        when(noteService.getNotes(anyLong())).thenReturn(List.of(note1, note2));

        mockMvc.perform(get("/api/v1/note/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .requestAttr("userId", 123L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].noteId").value(1L))
                .andExpect(jsonPath("$[1].noteId").value(2L));
    }

    @Test
    void getAllArchiveNotes_ShouldReturnArchiveNotes() throws Exception {
        var note1 = new NoteDto();
        note1.setNoteId(1L);
        when(noteService.getArchiveNotes(anyLong())).thenReturn(List.of(note1));

        mockMvc.perform(get("/api/v1/note/archive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .requestAttr("userId", 123L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].noteId").value(1L));
    }

    @Test
    void deleteNote_ShouldReturnSuccessMessage() throws Exception {
        doNothing().when(noteService).deleteNoteById(any(NoteDto.class), anyLong());

        mockMvc.perform(delete("/api/v1/note/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"noteId\":1}")
                        .requestAttr("userId", 123L))
                .andExpect(status().isOk());
    }

    @Test
    void createNote_ShouldValidateDate() throws Exception {
        when(noteService.createNote(any(NoteDto.class), anyLong()))
                .thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/v1/note/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"createDate\":\"2020-01-01\"}")
                        .requestAttr("userId", 123L))
                .andExpect(status().isBadRequest());
    }
}