package innopolis;

import innopolis.figures.Circle;
import innopolis.figures.Ellipse;
import innopolis.figures.Rectangle;
import innopolis.figures.Square;

import java.io.*;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/testFigures.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/testResults.txt"))) {
            var result = "";
            System.out.println("Начинаю чтение файла и запись результатов в новый файл... \n ----------------");
            while (reader.ready()) {
                var arg = List.of(reader.readLine().split("="));
                System.out.println("Вычитал данные: " + arg);
                switch (arg.getFirst()) {
                    case "Circle" -> result = new Circle(Double.parseDouble(arg.get(1)), 0, 0).toString();
                    case "Ellipse" -> result = new Ellipse(Double.parseDouble(arg.get(1).split(",")[0]), Double.parseDouble(arg.get(1).split(",")[1])).toString();
                    case "Rectangle" -> result = new Rectangle(Double.parseDouble(arg.get(1).split(",")[0]), Double.parseDouble(arg.get(1).split(",")[1])).toString();
                    case "Square" -> result = new Square(Double.parseDouble(arg.get(1)), 0, 0).toString();
                    default -> result = "Реализации для это фигуры нет";
                }
                System.out.println("Записываю результат: " + result);
                writer.write(result + "\n");
            }
            System.out.println("---------------- \nОперация завершина!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
