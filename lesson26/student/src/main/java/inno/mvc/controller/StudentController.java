package inno.mvc.controller;

import inno.mvc.model.StudentEntity;
import inno.mvc.model.UserRegistrationDto;
import inno.mvc.service.StudentService;
import inno.mvc.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final UserRegistrationService userRegistrationService;

    @PostMapping("/reg")
    public String registrationUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        userRegistrationService.registerUser(userRegistrationDto);
        return "Успешно зарегистрированы на платформе";
    }

    @PostMapping("/registration")
    public StudentEntity registration(StudentEntity student) {
        return studentService.registrationStudent(student);
    }

    @PostMapping("/signupCourse/{courseId}/student/{studentId}")
    public void addReviewAboutCourse(@PathVariable Integer courseId, @PathVariable Integer studentId) {
        studentService.signupCourse(studentId, courseId);
    }

    @GetMapping("/students/course/{courseId}")
    public List<StudentEntity> getStudentHasCourse(@PathVariable Integer courseId) {
        return studentService.getStudentsHasCourse(courseId);
    }

    @GetMapping("/students/course/more/{count}")
    public List<StudentEntity> getStudentMoreThanCountCourse(@PathVariable Integer count) {
        return studentService.getStudentsMoreThanCountCourse(count);
    }

    @GetMapping("/students/{name}/course/{courseName}")
    public List<StudentEntity> getStudentNameAndCourse(@PathVariable String name, @PathVariable String courseName) {
        return studentService.getStudentByNameAndCourse(name, courseName);
    }

    @PostMapping("/students/course/{courseXStudentId}")
    public void addReviewAboutCourse(@PathVariable Integer courseXStudentId, @RequestBody String review) {
        studentService.addReviewAboutCourse(courseXStudentId, review);
    }


}
