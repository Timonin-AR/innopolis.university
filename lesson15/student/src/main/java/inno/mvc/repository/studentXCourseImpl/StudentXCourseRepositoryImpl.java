package inno.mvc.repository.studentXCourseImpl;

import inno.mvc.repository.StudentXCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudentXCourseRepositoryImpl implements StudentXCourseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(Integer studentId, Integer courseId) {
        return jdbcTemplate.update("INSERT INTO university.student_x_course (studentId, courseId) values(?, ?)", studentId, courseId);
    }

    @Override
    public void findByStudentAndCourseId(Integer studentId, Integer courseId) {
       ;
    }


}
