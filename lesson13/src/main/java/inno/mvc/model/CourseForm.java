package inno.mvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseForm {
    @NotBlank(message = "Student ID is required.")
    private String courseName;
    @Positive
    private Integer studentId;
}
