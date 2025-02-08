package inno.mvc.repository.studentImpl;

import inno.mvc.model.Student;
import inno.mvc.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {

    private final DriverManagerDataSource dataSource;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public Student create(Student student) {
        return jdbcTemplate.queryForObject("insert into university.student (fio, email) values(?, ?) RETURNING *", new BeanPropertyRowMapper<>(Student.class), student.getFio(), student.getEmail());
    }

    @Override
    public Student findById(Integer id) {
        return jdbcTemplate.queryForObject("select * from university.student where id=?", new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query("select * from university.student", new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public void updateCourse(Integer id, String courseName) {
        jdbcTemplate.update("update university.student us set course=? where us.id = ?", courseName, id);
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("delete from university.student us where us.id = ?", id);
    }
}
