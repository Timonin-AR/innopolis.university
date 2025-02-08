import inno.mvc.controller.StudentController;
import inno.mvc.model.Student;
import inno.mvc.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
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
        mockMvc.perform(get("/registration")).andExpect(status().isOk());
    }

    @Test
    void signupCourseTest() throws Exception {
        var student = new Student(1, "ФИО", "timonin@gmail.com", null);
        when(studentService.registrationStudent(any())).thenReturn(student);
        mockMvc.perform(post("/signupCourse")
                        .param("courseName", "java", "spring")
                        .flashAttr("student", student))
                .andExpect(status().isOk());
        verify(studentService, times(1)).registrationStudent(any());
    }


    @Test
    void congratulateTest() throws Exception {
        var student = new Student(1, "ФИО", "timonin@gmail.com", "java");
        when(studentService.signupCourse(any(), any())).thenReturn(student.getCourse());
        mockMvc.perform(get("/congratulate")).andExpect(status().isOk());
        verify(studentService, times(1)).signupCourse(any(), any());
    }

}
