package inno.mvc.repository;

import inno.mvc.model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM university.student s where s.id in (select distinct studentId from university.student_x_course where university.student_x_course.courseid = ?)")
    List<StudentEntity> findAllStudentHasCourse(Integer courseId);


}
