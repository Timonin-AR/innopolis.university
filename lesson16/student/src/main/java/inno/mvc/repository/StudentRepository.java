package inno.mvc.repository;

import inno.mvc.model.Student;

import java.util.List;

public interface StudentRepository {
    Student create(Student student);

    Student findById(Integer id);

    List<Student> findAll();
    List<Student> findAllStudentHasCourse(Integer courseId);

    void updateCourse(Integer id, String courseName);

    void delete(Integer id);

}
