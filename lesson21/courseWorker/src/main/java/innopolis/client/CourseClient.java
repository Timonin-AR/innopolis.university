package innopolis.client;

import innopolis.model.Course;

import java.util.List;

public interface CourseClient {

    Course findById(Integer  id);
    List<Course> findAll();
    void deactivateCourse(Integer id);
}
