import inno.mvc.client.CourseRestClientImpl;
import inno.mvc.controller.StudentController;
import inno.mvc.model.Course;
import inno.mvc.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();

    }

    @Test
    void registrationTest() throws Exception {
        mockMvc.perform(post("/registration")).andExpect(status().isOk());
    }

    @Test
    void signupCourseTest() throws Exception {

        mockMvc.perform(post("/signupCourse/{courseId}/student/{studentId}", 3, 1)).andExpect(status().isOk());
        verify(studentService, times(1)).signupCourse(1, 3);
    }

}
