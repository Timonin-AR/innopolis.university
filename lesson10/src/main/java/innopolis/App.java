package innopolis;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Predicate<Object> condition = Objects::isNull;
        Function<Object, Integer> ifTrue = str -> 0;
        Function<CharSequence, Integer> ifFalse = CharSequence::length;

        Function<String, Integer> ternaryOperator = str -> condition.test(str) ? ifTrue.apply(str) : ifFalse.apply(str);
        var str = getStringFromFile("src/main/resources/string.txt");
        System.out.println(ternaryOperator.apply(null));
        recordResultIntoFile(str);
        System.out.println(ternaryOperator.apply(str));
    }

    public static void recordResultIntoFile(String result) {
        try {
            Files.writeString(Path.of("src/main/resources/results.txt"), result + "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getStringFromFile(String path) {
        try (var stream = Files.lines(Path.of(path))) {
            return stream.findFirst().orElseThrow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
