package innopolis.controller;

import innopolis.entity.Course;
import innopolis.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController()
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/create")
    public void createCourse(@Valid @RequestBody Course course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new RuntimeException("Данные заполнены не корректно");
        courseService.createCourse(course);
    }

    @PostMapping("/course")
    public void updateCourse(@RequestBody Course course) {
        courseService.updateCourse(course);
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseById(id));
    }

    @GetMapping("/course")
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @PostMapping("/deactivate/{id}")
    public void deactivateCourse(@PathVariable Integer id) {
        courseService.deactivateCourse(id);
    }
}
