package inno.mvc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(schema = "university", name = "student")
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "fio")
    @Size(min = 2, max = 150, message = "Min value 2 and max 150 symbols")
    @NotBlank(message = "Field should be not empty")
    private String fio;

    @Column(name = "email")
    @Email(message = "Enter the correct email address")
    @NotBlank(message = "Field should be not empty")
    private String email;

    @Column(name = "course")
    private String course;

}
