package innopolis;


import com.fasterxml.jackson.databind.ObjectMapper;
import innopolis.controller.CourseController;
import innopolis.entity.Course;
import innopolis.service.CourseService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    void createCourseTest() throws Exception {
        var course = new Course(1, "java", true, Timestamp.valueOf(LocalDateTime.now()));
        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk());
        verify(courseService, times(1)).createCourse(any());
    }

    @Test
    void updateCourseTest() throws Exception {
        var course = new Course(1, "java", true, Timestamp.valueOf(LocalDateTime.now()));
        mockMvc.perform(post("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk());
        verify(courseService, times(1)).updateCourse(any());
    }

    @Test
    void getCourseTest() throws Exception {
        mockMvc.perform(get("/course/{id}", 1))
                .andExpect(status().isOk());
        verify(courseService, times(1)).getCourseById(any());
    }

    @Test
    void getCoursesTest() throws Exception {
        mockMvc.perform(get("/course"))
                .andExpect(status().isOk());
        verify(courseService, times(1)).getCourses();
    }

    @Test
    void deactivateCourseTest() throws Exception {
        mockMvc.perform(post("/deactivate/{id}", 1))
                .andExpect(status().isOk());
        verify(courseService, times(1)).deactivateCourse(1);
    }


}
