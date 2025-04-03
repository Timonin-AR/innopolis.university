import inno.mvc.controller.StudentController;
import inno.mvc.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    void getStudentHasCourseTest() throws Exception {
        mockMvc.perform(get("/students/course/{courseId}", 4)).andExpect(status().isOk());
        verify(studentService, times(1)).getStudentsHasCourse(4);
    }

    @Test
    void getStudentMoreThanCountCourseTest() throws Exception {
        mockMvc.perform(get("/students/course/more/{count}", 2)).andExpect(status().isOk());
        verify(studentService, times(1)).getStudentsMoreThanCountCourse(2);
    }

    @Test
    void getStudentNameAndCourseTest() throws Exception {
        mockMvc.perform(get("/students/{name}/course/{courseName}", "Miron", "java")).andExpect(status().isOk());
        verify(studentService, times(1)).getStudentByNameAndCourse("Miron", "java");
    }

    @Test
    void addReviewAboutCourseTest() throws Exception {
        mockMvc.perform(post(
                "/students/course/{courseXStudentId}", 10)
                .contentType(MediaType.TEXT_PLAIN)
                .content("коммент о курсе"))
                .andExpect(status().isOk());
        verify(studentService, times(1)).addReviewAboutCourse(any(), any());
    }

}
