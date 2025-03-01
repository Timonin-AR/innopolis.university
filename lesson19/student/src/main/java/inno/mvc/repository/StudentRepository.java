package inno.mvc.repository;

import inno.mvc.model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM university.student s where s.id in (select distinct studentId from university.student_x_course where university.student_x_course.courseid = ?)")
    List<StudentEntity> findAllStudentHasCourse(Integer courseId);

    @Query(value = """
            SELECT s
            FROM StudentEntity s, StudentXCourseEntity sxce
            WHERE sxce.studentId = s.id
            GROUP BY s
            HAVING COUNT(sxce) >= :moreThanCourse""")
    List<StudentEntity> findStudentByCountCourse(@Param("moreThanCourse") Integer moreThanCourse);

    @Query(value = """
            SELECT s
            FROM StudentEntity s
            WHERE s.fio = :fio AND s.course LIKE CONCAT('%', :courseName, '%')""")
    List<StudentEntity> findStudentByNameAndContainsCourse(@Param("fio") String fio, @Param("courseName") String courseName);


}
