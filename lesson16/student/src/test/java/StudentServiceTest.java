import inno.mvc.client.CourseRestClientImpl;
import inno.mvc.model.Course;
import inno.mvc.model.Student;
import inno.mvc.repository.studentImpl.StudentRepositoryImpl;
import inno.mvc.repository.studentXCourseImpl.StudentXCourseRepositoryImpl;
import inno.mvc.service.StudentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepositoryImpl studentRepository;

    @Mock
    private StudentXCourseRepositoryImpl studentXCourse;

    @Mock
    private CourseRestClientImpl courseRestClient;

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
        when(courseRestClient.findById(1)).thenReturn(new Course(1, "java", true, Timestamp.valueOf(LocalDateTime.now())));
        when(studentRepository.findById(3)).thenReturn(new Student(3, "ФИО", "почта@маил.ру", null));
        when(studentXCourse.create(3, 1)).thenReturn(1);
        doNothing().when(studentRepository).updateCourse(3, "java");
        assertThat(studentService.signupCourse(3, 1)).isEqualTo("java");
    }

    @Test
    void getStudentsHasCourseTest() {
        when(studentRepository.findAllStudentHasCourse(4)).thenReturn(List.of(new Student()));
        Assertions.assertThat(studentService.getStudentsHasCourse(4)).hasSizeGreaterThan(0);
    }


}
