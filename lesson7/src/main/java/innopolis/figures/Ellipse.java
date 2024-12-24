package innopolis.figures;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ellipse extends Figure {
    private Double r;
    private Double R;

    @Override
    public Double getPerimeter() {
        return 2 * PI * Math.sqrt((Math.pow(R, 2) + Math.pow(r, 2)) / 2);
    }
}
