package inno.mvc.repository;

public interface StudentXCourseRepository {
    Integer create(Integer studentId, Integer courseId);
    void findByStudentAndCourseId(Integer studentId, Integer courseId);
}
