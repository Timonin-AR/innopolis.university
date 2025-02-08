import inno.mvc.model.Student;
import inno.mvc.repository.studentImpl.StudentRepositoryImpl;
import inno.mvc.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepositoryImpl studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void registrationTest() {
        var student = new Student(null, "ФИО", "почта@маил.ру", null);
        when(studentRepository.create(student)).thenReturn(new Student(1, "ФИО", "почта@маил.ру", null));
        assertThat(studentService.registrationStudent(student))
                .extracting(Student::getId, Student::getFio, Student::getEmail, Student::getCourse)
                .containsExactly(1, student.getFio(), student.getEmail(), null);
    }

    @Test
    void signupCourseTest() {
        doNothing().when(studentRepository).updateCourse(1, "java");
        assertThat(studentService.signupCourse(1, new String[]{"java"})).isEqualTo("java");
    }


}
