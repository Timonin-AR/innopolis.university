package innopolis;

import innopolis.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseRepository implements ICourseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Course create(Course course) {
        return jdbcTemplate.queryForObject("insert into uni.course (courseName, isActive, startDate) values(?, ?, ?) RETURNING *", new BeanPropertyRowMapper<>(Course.class), course.getCourseName(), course.getIsActive(), course.getStartDate());
    }

    @Override
    public Course read(Integer id) {
        return jdbcTemplate.queryForObject("select * from uni.course where id=?", new BeanPropertyRowMapper<>(Course.class), id);
    }

    @Override
    public List<Course> readAll() {
        return jdbcTemplate.query("select * from uni.course", new BeanPropertyRowMapper<>(Course.class));
    }

    @Override
    public Integer update(Course course) {
        return jdbcTemplate.update("update uni.course  set coursename=?, isactive=?, startdate=? where id = ?",
                course.getCourseName(), course.getIsActive(), course.getStartDate(), course.getId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("delete from uni.course uc where uc.id = ?", id);
    }
}
