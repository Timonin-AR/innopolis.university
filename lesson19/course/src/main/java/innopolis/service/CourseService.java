package innopolis.service;

import innopolis.entity.Course;
import innopolis.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public Course createCourse(Course course) {
        return courseRepository.create(course);
    }

    public Course getCourseById(Integer id) {
        return courseRepository.read(id);
    }

    public Integer updateCourse(Course course) {
        return courseRepository.update(course);
    }

    public List<Course> getCourses() {
        return courseRepository.readAll();
    }

    public void deactivateCourse(Integer id) {
        var course = courseRepository.read(id);
        course.setIsActive(false);
        updateCourse(course);
    }
}
