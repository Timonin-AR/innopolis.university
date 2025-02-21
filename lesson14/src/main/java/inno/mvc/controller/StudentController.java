package inno.mvc.controller;

import inno.mvc.model.CourseForm;
import inno.mvc.model.Student;
import inno.mvc.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @RequestMapping("")
    public String showIndex() {
        return "index";
    }

    @PostMapping("/signupCourse")
    public String signupCourse(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration-view";
        } else {
            student.setId(studentService.registrationStudent(student).getId());
        }
        var courseForm = new CourseForm();
        courseForm.setStudentId(student.getId());
        model.addAttribute("courseForm", courseForm);
        return "signup-course-view";
    }

    @RequestMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("student", new Student());
        return "registration-view";
    }

    @RequestMapping("/congratulate")
    public String congratulate(@Valid @ModelAttribute("courseName") CourseForm courseName, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup-course-view";
        } else {
            studentService.signupCourse(courseName.getStudentId(), courseName.getCourseName());
            return "congratulate-course-view";
        }
    }

}
