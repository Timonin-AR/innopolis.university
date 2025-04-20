package innopolis;

import innopolis.entity.Course;

import java.util.List;

public interface ICourseRepository {
    Course create(Course course);
    Course read(Integer id);
    List<Course> readAll();
    Integer update(Course course);
    void delete(Integer id);
}
