package inno.mvc.client;

import inno.mvc.model.Course;

import java.util.List;

public interface CourseClient {

    Course findById(Integer  id);
    List<Course> findAll();
}
