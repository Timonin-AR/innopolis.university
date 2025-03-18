package inno.mvc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "university", name = "student_x_course")
public class StudentXCourseEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private Integer studentId;
    @Column
    private Integer courseId;
}
