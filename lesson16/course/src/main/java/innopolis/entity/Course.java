package innopolis.entity;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Course {
    private Integer id;
    @NotBlank(message = "Field should be not empty")
    private String courseName;
    private Boolean isActive;
    private Timestamp startDate;
}
