package innopolis.figures;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rectangle extends Figure {
    private Double length;
    private Double width;

    @Override
    public Double getPerimeter() {
        return (length + width) * 2;
    }
}
