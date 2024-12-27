package innopolis;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        var taskResult = "";
        //a) Создать коллекцию HashMap с типом элементов String. Добавить в неё 10строк(предпочтительно чтение из файла)
        var hashMap = getMapFromFile("src/main/resources/mapKyesAndValues.txt");
        taskResult = "Создание хеш-мап из файла: " + hashMap;
        System.out.println(taskResult);
        recordResultIntoFile(taskResult);

        //b) Добавить в коллекцию минимум пять пар «ключ-значение». Несколько ключей должны иметь одно и то же значение.
        hashMap.putAll(Map.of("11", "Олег", "12", "Мирон", "13", "Леха", "14", "Олег", "15", "Петя"));
        taskResult = "Добавлено 5 новых элементов, некоторые из них имею одно и то же значение" + hashMap;
        System.out.println(taskResult);
        recordResultIntoFile(taskResult);

        //c) Выполнить прямой перебор коллекции используя цикл for.
        taskResult = "Перебор мапы циклом for: ";
        System.out.println(taskResult);
        recordResultIntoFile(taskResult);
        for (Map.Entry entry : hashMap.entrySet()) {
            var keyValue = entry.getKey() + " : " + entry.getValue();
            System.out.println(keyValue);
            recordResultIntoFile(keyValue);
        }

        //d) Добавить новый элемент с уже имеющимся в отображении ключом.
        hashMap.put("1", "Ибрагим");
        taskResult = "Добавление элемента с уже имеющимся ключом '1': " + hashMap;
        System.out.println(taskResult);
        recordResultIntoFile(taskResult);

        //e Вынести список всех ключей из HashMap в отдельную коллекцию.
        var list = List.of(hashMap.keySet());
        taskResult = "Вывод всех ключей в коллекцию: " + list;
        System.out.println(taskResult);
        recordResultIntoFile(taskResult);

        //f) Вынести список всех значений из HashMap в коллекцию HashSet и получить количество уникальных значений.
        var hashSet = new HashSet<>(hashMap.values());
        taskResult = String.format("Количество уникальных значений = %s. Уникальные значения полученные из хеш-мап: %s", hashSet.size(), hashSet);
        System.out.println(taskResult);
        recordResultIntoFile(taskResult);

        //g) Определить, содержит ли коллекция HashMap определенный ключ.
        taskResult = "Содержит ли хеш-мап ключ '2'? : " + hashMap.containsKey("2");
        System.out.println(taskResult);
        recordResultIntoFile(taskResult);

        //h) Определить, содержит ли коллекция HashMap определенное значение.
        taskResult = "Содержит ли хеш-мап значение 'Тёма'? : " + hashMap.containsValue("Тёма");
        System.out.println(taskResult);
        recordResultIntoFile(taskResult);

        //i) Получить количество элементов, содержащихся в данный момент в коллекции HashMap.
        taskResult = "Количество элементов содержащиеся в хеш-мап: " + hashMap.size();
        System.out.println(taskResult);
        recordResultIntoFile(taskResult);

        //j) Удалить из коллекции выбранный объект по ключу и по значению.
        hashMap.remove("7", "Петя");
        taskResult = "Удалить из хеш-мап значение по ключу '7' и значению 'Петя'" + hashMap;
        System.out.println(taskResult);
        recordResultIntoFile(taskResult);



    }

    public static void recordResultIntoFile(String result) {
        try {
            Files.writeString(Path.of("src/main/resources/results.txt"), result + "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<String, String> getMapFromFile(String path) {
        try (var stream = Files.lines(Path.of(path))) {
            return (HashMap<String, String>) stream.flatMap(lines -> Stream.of(lines.split(","))
                            .map(pairs -> pairs.split(":")))
                    .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
