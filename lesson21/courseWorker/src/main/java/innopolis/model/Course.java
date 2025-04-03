package innopolis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private Integer id;
    private String courseName;
    private Boolean isActive;
    private Timestamp startDate;
}
