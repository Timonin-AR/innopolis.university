import inno.mvc.client.CourseRestClientImpl;
import inno.mvc.model.Course;
import inno.mvc.model.StudentEntity;
import inno.mvc.model.StudentXCourseEntity;
import inno.mvc.repository.StudentRepository;
import inno.mvc.repository.StudentXCourseRepository;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentXCourseRepository studentXCourse;

    @Mock
    private CourseRestClientImpl courseRestClient;

    @InjectMocks
    private StudentService studentService;

    @Test
    void registrationTest() {
        var student = new StudentEntity(null, "ФИО", "почта@маил.ру", null);
        when(studentRepository.save(student)).thenReturn(new StudentEntity(1, "ФИО", "почта@маил.ру", null));
        assertThat(studentService.registrationStudent(student))
                .extracting(StudentEntity::getId, StudentEntity::getFio, StudentEntity::getEmail, StudentEntity::getCourse)
                .containsExactly(1, student.getFio(), student.getEmail(), null);
    }

    @Test
    void signupCourseTest() {
        when(courseRestClient.findById(1)).thenReturn(new Course(1, "java", true, Timestamp.valueOf(LocalDateTime.now())));
        when(studentRepository.findById(3)).thenReturn(Optional.of(new StudentEntity(3, "ФИО", "почта@маил.ру", null)));
        when(studentXCourse.save(new StudentXCourseEntity(null,3, 1))).thenReturn(any());
        assertThat(studentService.signupCourse(3, 1)).isEqualTo("java");
    }

    @Test
    void getStudentsHasCourseTest() {
        when(studentRepository.findAllStudentHasCourse(4)).thenReturn(List.of(new StudentEntity()));
        Assertions.assertThat(studentService.getStudentsHasCourse(4)).hasSizeGreaterThan(0);
    }

    @Test
    void getStudentsMoreThanCountCourseTest() {
        when(studentRepository.findStudentByCountCourse(4)).thenReturn(List.of(new StudentEntity()));
        Assertions.assertThat(studentService.getStudentsMoreThanCountCourse(4)).hasSizeGreaterThan(0);
    }

    @Test
    void getStudentByNameAndCourseTest() {
        when(studentRepository.findStudentByNameAndContainsCourse("Miron", "java")).thenReturn(List.of(new StudentEntity()));
        Assertions.assertThat(studentService.getStudentByNameAndCourse("Miron", "java")).hasSizeGreaterThan(0);
    }




}
