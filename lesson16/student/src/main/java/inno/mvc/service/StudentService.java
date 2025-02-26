package inno.mvc.service;

import inno.mvc.client.CourseRestClientImpl;
import inno.mvc.model.Student;
import inno.mvc.repository.studentImpl.StudentRepositoryImpl;
import inno.mvc.repository.studentXCourseImpl.StudentXCourseRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final CourseRestClientImpl courseRestClient;
    private final StudentRepositoryImpl studentRepository;
    private final StudentXCourseRepositoryImpl studentXCourse;

    public Student registrationStudent(Student student) {
        return studentRepository.create(student);
    }

    public String signupCourse(Integer studentId, Integer courseId) {
        var course = courseRestClient.findById(courseId);
        var student = studentRepository.findById(studentId);

        //Проверяем, что курс активный и что студент на него не записан
        if (course.getIsActive() && student.getCourse() == null || !student.getCourse().contains(course.getCourseName())) {
            studentXCourse.create(studentId, courseId);
            var courseName = student.getCourse() != null ? student.getCourse() : "";
            studentRepository.updateCourse(studentId, String.join(courseName, " ", course.getCourseName()).trim());
        } else throw new RuntimeException("Студент записан на курс или курс уже закончился");

        return course.getCourseName();
    }

    public List<Student> getStudentsHasCourse(Integer courseId) {
        return studentRepository.findAllStudentHasCourse(courseId);
    }
}
