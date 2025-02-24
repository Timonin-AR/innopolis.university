package inno.mvc.controller;

import inno.mvc.model.Student;
import inno.mvc.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/registration")
    public Student registration(Student student) {
        return studentService.registrationStudent(student);
    }

    @PostMapping("/signupCourse/{courseId}/student/{studentId}")
    public void signupCourse(@PathVariable Integer courseId, @PathVariable Integer studentId) {
        studentService.signupCourse(studentId, courseId);
    }

    @GetMapping("/students/course/{courseId}")
    public List<Student> getStudentHasCourse(@PathVariable Integer courseId) {
        return studentService.getStudentsHasCourse(courseId);
    }


}
