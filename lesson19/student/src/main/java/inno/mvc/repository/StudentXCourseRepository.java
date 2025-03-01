package inno.mvc.repository;

import inno.mvc.model.StudentXCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentXCourseRepository extends JpaRepository<StudentXCourseEntity, Integer> {
}
