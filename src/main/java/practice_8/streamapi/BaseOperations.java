package practice_8.streamapi;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BaseOperations {
    public static void main(String[] args) {
        //1
        List<String> list = Arrays.asList("Banana", "Orange", "Apple", "Kiwi");
        List<String> result = list.stream()
                .filter(n -> n.length() > 5)
                .collect(Collectors.toList());
        System.out.println(result);

        //2
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> dividedByFive = numbers.stream()
                .filter(n -> n % 5 == 0)
                .collect(Collectors.toList());
        System.out.println(dividedByFive);

        //3
        List<String> strings = Arrays.asList("Banana", "Orange", "Apple", "Kiwi");
        List<Integer> lengthOfStrings = strings.stream()
                .map(s -> s.length())
                .collect(Collectors.toList());
        System.out.println(lengthOfStrings);

        //4
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squareNumbers = nums.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());
        System.out.println(squareNumbers);

        //5
        List<Integer> nums1 = Arrays.asList(1, 2, 3, 4, 5, 1, 2 ,5, 10);
        List<Integer> uniqueNumbers = nums1.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(uniqueNumbers);



    }
}
