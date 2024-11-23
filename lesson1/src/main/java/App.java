import java.util.List;

public class App {
    public static void main(String[] args) {
        List<Product> products = List.of(
                new Product(1, 5, 30, "Яблоко"),
                new Product(2, 5, 20, "Груша"),
                new Product(3, 6, 10, "Хурма"));
        products.forEach(System.out::println);
    }
}
