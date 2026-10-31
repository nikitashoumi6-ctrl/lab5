import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LabSolution {

    public static void main(String[] args) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Visitor>>() {}.getType();

        List<Visitor> visitors;
        try (Reader reader = new FileReader("books.json")) {
            visitors = gson.fromJson(reader, type);
        } catch (IOException e) {
            System.err.println("Ошибка чтения books.json: " + e.getMessage());
            return;
        }

        // Задание 1
        System.out.println("Задание 1");
        long visitorCount = visitors.stream().count();
        System.out.println("Количество посетителей: " + visitorCount);
        visitors.stream().forEach(v -> System.out.println(v.getName() + " " + v.getSurname()));

        // Задание 2
        System.out.println("\nЗадание 2");
        List<Book> uniqueBooks = visitors.stream()
                .flatMap(v -> v.getFavoriteBooks().stream())
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Количество уникальных книг: " + uniqueBooks.stream().count());
        uniqueBooks.stream().forEach(b -> System.out.println(b.getName() + " — " + b.getAuthor()));

        // Задание 3
        System.out.println("\nЗадание 3");
        visitors.stream()
                .flatMap(v -> v.getFavoriteBooks().stream())
                .distinct()
                .sorted(Comparator.comparingInt(Book::getPublishingYear))
                .forEach(b -> System.out.println(b.getPublishingYear() + " — " + b.getName()));

        // Задание 4
        System.out.println("\nЗадание 4");
        boolean hasJaneAusten = visitors.stream()
                .flatMap(v -> v.getFavoriteBooks().stream())
                .anyMatch(b -> "Jane Austen".equalsIgnoreCase(b.getAuthor()));
        System.out.println("Есть ли книга Jane Austen: " + hasJaneAusten);

        // Задание 5
        System.out.println("\nЗадание 5");
        int maxBooks = visitors.stream()
                .mapToInt(v -> v.getFavoriteBooks().size())
                .max()
                .orElse(0);
        System.out.println("Максимальное число книг: " + maxBooks);

        // Задание 6
        System.out.println("\nЗадание 6");
        double avgBooks = visitors.stream()
                .mapToInt(v -> v.getFavoriteBooks().size())
                .average()
                .orElse(0.0);

        Map<String, List<SmsMessage>> groupedMessages = visitors.stream()
                .filter(Visitor::isSubscribed)
                .collect(Collectors.groupingBy(
                        v -> {
                            int count = v.getFavoriteBooks().size();
                            if (count > avgBooks) return "bookworm";
                            if (count < avgBooks) return "read_more";
                            return "fine";
                        },
                        Collectors.mapping(v -> {
                            int count = v.getFavoriteBooks().size();
                            String msg = count > avgBooks ? "you are a bookworm" :
                                    count < avgBooks ? "read more" : "fine";
                            return new SmsMessage(v.getPhone(), msg);
                        }, Collectors.toList())
                ));

        groupedMessages.forEach((category, list) -> {
            System.out.println("Категория: " + category);
            list.stream().forEach(m -> System.out.println("  " + m.getPhoneNumber() + " -> " + m.getMessage()));
        });
    }
}