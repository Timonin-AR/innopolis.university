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

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @RequestMapping("")
    public String showIndex() {
        return "index";
    }

    @RequestMapping("/signupCourse")
    public String signupCourse(Model model) {
        model.addAttribute("courseForm", new CourseForm());
        return "signup-course-view";
    }

    @RequestMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("student", new Student());
        return "registration-view";
    }

    @RequestMapping("/congratulate")
    public String congratulate(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration-view";
        } else {
            studentService.registrationStudent(student);
            student.setFio("Mr. " + student.getFio());
            return "congratulate-view";
        }
    }

    @PostMapping("/congratulateCourse")
    public String congratulateCourse(@Valid @ModelAttribute("courseName") CourseForm courseName, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup-course-view";
        } else {
            studentService.signupCourse(courseName.getStudentId(), courseName.getCourseName());
            return "congratulate-course-view";
        }
    }
}
