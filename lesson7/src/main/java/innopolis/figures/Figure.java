package innopolis.figures;

public abstract class Figure {
    public Double PI = Math.PI;

    public Double getPerimeter() {
        return (double) 0;
    }

    @Override
    public String toString() {
        return String.format("Периметр фигур %s равен %s", Figure.this.getClass().getName().split("\\.")[2], getPerimeter());
    }
}
