package practice_8.streamapi;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AggregationOperations {
    public static void main(String[] args) {
        //1
        List<Integer> numbers = Arrays.asList(10, 50, 8, 6, 19, 80);
        Integer findMax = numbers.stream()
                .max(Comparator.naturalOrder())
                .get();
        System.out.println(findMax);

        //2
        Integer findMin = numbers.stream()
                .min(Comparator.naturalOrder())
                .get();
        System.out.println(findMin);

        //3
        int sum = numbers.stream()
                .mapToInt(n -> n)
                .sum();
        System.out.println(sum);

        //4
        List<String> strings = Arrays.asList("Banana1", "Orange", "Apple", "Banana2", "Kiwi");
        Optional<String> startsWithB = strings.stream()
                .filter(s -> s.startsWith("B"))
                .findFirst();
        System.out.println(startsWithB);

        //5
        boolean matched = numbers.stream()
                .anyMatch(n -> n % 2 == 0);
        System.out.println("Is any even number in list " + matched);

    }
}
