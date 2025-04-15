package innopolis;

import innopolis.entity.Course;
import innopolis.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void createCourseTest() {
        var course = new Course(0, "java", true, Timestamp.valueOf(LocalDateTime.now()));
        when(courseRepository.create(course)).thenReturn(course);
        assertThat(courseService.createCourse(course).getCourseName()).isEqualTo("java");
    }

    @Test
    void getCourseByIdTest() {
        var course = new Course(1, "java", true, Timestamp.valueOf(LocalDateTime.now()));
        when(courseRepository.read(1)).thenReturn(course);
        assertThat(courseService.getCourseById(1).getId()).isEqualTo(1);
    }

    @Test
    void updateCourseTest() {
        var course = new Course(1, "java", true, Timestamp.valueOf(LocalDateTime.now()));
        when(courseRepository.update(course)).thenReturn(1);
        assertThat(courseService.updateCourse(course)).isEqualTo(1);
    }

    @Test
    void getCoursesTest() {
        var course = new Course(1, "java", true, Timestamp.valueOf(LocalDateTime.now()));
        when(courseRepository.readAll()).thenReturn(List.of(course));
        assertThat(courseService.getCourses()).hasSizeGreaterThan(0);
    }


}
