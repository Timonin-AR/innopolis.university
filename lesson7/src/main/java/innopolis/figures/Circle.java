package innopolis.figures;

import innopolis.interfaces.Coordinate;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Circle extends Ellipse implements Coordinate {
    private Double radius;
    private Integer x;
    private Integer y;

    @Override
    public void setCoordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Double getPerimeter() {
        return 2 * PI * radius;
    }

    public Double getDiameter() {
        return 2 * radius;
    }
}
