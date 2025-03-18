package inno.mvc.service;

import inno.mvc.client.CourseRestClientImpl;
import inno.mvc.model.StudentEntity;
import inno.mvc.model.StudentXCourseEntity;
import inno.mvc.repository.StudentRepository;
import inno.mvc.repository.StudentXCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final CourseRestClientImpl courseRestClient;
    private final StudentRepository studentRepository;
    private final StudentXCourseRepository studentXCourse;

    @Autowired
    public StudentService(CourseRestClientImpl courseRestClient, StudentRepository studentRepository, StudentXCourseRepository studentXCourse) {
        this.courseRestClient = courseRestClient;
        this.studentRepository = studentRepository;
        this.studentXCourse = studentXCourse;
    }

    public StudentEntity registrationStudent(StudentEntity student) {
        return studentRepository.save(student);
    }

    public String signupCourse(Integer studentId, Integer courseId) {
        var course = courseRestClient.findById(courseId);
        var student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            //Проверяем, что курс активный и что студент на него не записан
            if (course.getIsActive() && student.get().getCourse() == null || !student.get().getCourse().contains(course.getCourseName())) {
                studentXCourse.save(new StudentXCourseEntity(null, studentId, courseId));
                var courseName = student.get().getCourse() != null ? student.get().getCourse() : "";
                student.get().setCourse(String.join(courseName, " ", course.getCourseName()).trim());
                studentRepository.save(student.get());
            } else throw new RuntimeException("Студент записан на курс или курс уже закончился");
        } else throw new RuntimeException("Нет данных по студенту");

        return course.getCourseName();
    }

    public List<StudentEntity> getStudentsHasCourse(Integer courseId) {
        return studentRepository.findAllStudentHasCourse(courseId);
    }

    public List<StudentEntity> getStudentsMoreThanCountCourse(Integer count) {
        return studentRepository.findStudentByCountCourse(count);
    }

    public List<StudentEntity> getStudentByNameAndCourse(String name, String courseName) {
        return studentRepository.findStudentByNameAndContainsCourse(name, courseName);
    }
}
