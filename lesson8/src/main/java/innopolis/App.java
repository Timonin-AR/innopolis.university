package innopolis;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        //Исследование List
        //a) Создайте массив из N чисел (предпочтительно чтение из файла).
        var array = getArrayFromFile("src/main/resources/numbers.txt");
        System.out.println("Создан массив из файла: " + Arrays.toString(array));
        recordResultIntoFile(Arrays.toString(array));

        //b) На основе массива создайте список List.
        var list = new ArrayList<>(Arrays.stream(array).boxed().toList());
        System.out.println("Создан список из массива: " + list);
        recordResultIntoFile(list.toString());

        //c) Отсортируйте список в натуральном порядке
        list.sort(Comparator.naturalOrder());
        System.out.println("Список отсортирован в натуральной последовательности: " + list);
        recordResultIntoFile(list.toString());

        //d) Отсортируйте список в обратном порядке
        list.sort(Comparator.reverseOrder());
        System.out.println("Список перевернут: " + list);
        recordResultIntoFile(list.toString());

        //e) Перемешайте список
        Collections.shuffle(list);
        System.out.println("Список перемешен: " + list);
        recordResultIntoFile(list.toString());

        //f) Выполните циклический сдвиг на 1 элемент.
        var listShifted = shiftCycle(list, 1);
        System.out.println("Список сдвинуть на 1 элемент : " + listShifted);
        recordResultIntoFile(listShifted.toString());

        //g) Оставьте в списке только уникальные элементы.
        var listDistinct = list.stream().distinct().toList();
        System.out.println("Список из уникальных значений: " + listDistinct);
        recordResultIntoFile(listDistinct.toString());

        //h) Оставьте в списке только дублирующиеся элементы
        var listDoubleValues = list.stream().filter(findDuplicate()).toList();
        System.out.println("Список из дублирующих значений: " + listDoubleValues);
        recordResultIntoFile(listDoubleValues.toString());

        //i) Из списка получите массив
        int[] listToArray = list.stream().mapToInt(element -> element).toArray();
        System.out.println("Возвращаем обратно в массив: " + Arrays.toString(listToArray));
        recordResultIntoFile(Arrays.toString(listToArray));


        //Исследование HasSet
        var stringResultOfSet = "";
        //a) Создать коллекцию HashSet с типом элементов String. Добавить в неё 10 строк(предпочтительно чтение из файла).
        var set = new HashSet<>(getSetFromFile("src/main/resources/strings.txt"));
        stringResultOfSet = "Создания множества из файла: " + set;
        System.out.println(stringResultOfSet);
        recordResultIntoFile(stringResultOfSet);

        //b) Добавить в множество минимум пять объектов, которые являются совместимыми с типом данных коллекции.
        set.addAll(Set.of("Одиннадцать", "Двенадцать", "Тринадцать", "Четырнадцать", "Пятнадцать"));
        stringResultOfSet = "Добавление в множество 5 объевтов: " + set;
        System.out.println(stringResultOfSet);
        recordResultIntoFile(stringResultOfSet);

        //c) Вывести на экран элементы множества используя цикл for
        stringResultOfSet = "Вывод на экран элементы множества: ";
        recordResultIntoFile(stringResultOfSet);
        System.out.println(stringResultOfSet);
        for (String str : set) {
            recordResultIntoFile(str);
            System.out.println(str);
        }

        //d) Добавить новый элемент, который уже присутствует в множестве.
        set.add("Один");
        stringResultOfSet = "Добавление существующего элемента 'Один': " + set;
        System.out.println(stringResultOfSet);
        recordResultIntoFile(stringResultOfSet);

        //e) Определить, содержит ли коллекция определенный объект.
        stringResultOfSet = "Содержит ли коллекци объект 'Два'?: " + set.contains("Два");
        System.out.println(stringResultOfSet);
        recordResultIntoFile(stringResultOfSet);

        //f) Удалить из коллекции любой объект.
        set.remove("Три");
        stringResultOfSet = "Объект 'Три' удален: " + set;
        System.out.println(stringResultOfSet);
        recordResultIntoFile(stringResultOfSet);

        //g) Получить количество элементов, содержащеюся в коллекции на данный момент.
        stringResultOfSet = "Количество элементов в мновестве: " + set.size();
        System.out.println(stringResultOfSet);
        recordResultIntoFile(stringResultOfSet);

        //h) Удалить все элементы множества
        stringResultOfSet = "Удалить все элементы";
        set.clear();
        System.out.println(stringResultOfSet);
        recordResultIntoFile(stringResultOfSet);

        //i) Проверить, является ли коллекция HashSet пустой
        stringResultOfSet = "Является ли множество пустым?: " + set.isEmpty();
        System.out.println(stringResultOfSet);
        recordResultIntoFile(stringResultOfSet);

    }


    public static int[] getArrayFromFile(String path) {
        try (var stream = Files.lines(Path.of(path))) {
            return stream.flatMap(line -> Stream.of(line.split(","))).mapToInt(Integer::parseInt).toArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Set<String> getSetFromFile(String path) {
        try (var stream = Files.lines(Path.of(path))) {
            return stream.flatMap(line -> Stream.of(line.split(","))).collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Predicate<Integer> findDuplicate() {
        final Set<Integer> seen = new HashSet<>();
        return number -> !seen.add(number);
    }

    public static void recordResultIntoFile(String result) {
        try {
            Files.writeString(Path.of("src/main/resources/results.txt"), result + "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Integer> shiftCycle(List<Integer> list, Integer shiftOn) {
        for (int i = 0; i < shiftOn; i++) {
            var temp = list.getLast();
            list.removeLast();
            list.addFirst(temp);
        }
        return list;
    }


}
