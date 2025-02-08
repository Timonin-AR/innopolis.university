package inno.mvc.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Integer id;
    @Size(min = 2, max = 150, message = "Min value 2 and max 150 symbols")
    @NotBlank(message = "Field should be not empty")
    private String fio;
    @Email(message = "Enter the correct email address")
    @NotBlank(message = "Field should be not empty")
    private String email;
    private String course;

}
