package innopolis.figures;

import innopolis.interfaces.Coordinate;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Square extends Rectangle implements Coordinate {
    private Double a;
    private Integer x;
    private Integer y;

    @Override
    public void setCoordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Double getPerimeter() {
        return a * 4;
    }
}
