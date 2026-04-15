package practice_8.streamapi;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupingAndReduction {
    public static void main(String[] args) {
        //1
        List<String> strings = Arrays.asList("Banana1", "Orange", "Apple", "Banana2", "Kiwi", "Orange1");
        Map<Character, List<String>> groupedStrings = strings.stream()
                .collect(Collectors.groupingBy(s -> s.charAt(0)));
        System.out.println(groupedStrings);

        //2
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        Map<Boolean, List<Integer>> result = nums.stream()
                .collect(Collectors.groupingBy(n -> n % 2 == 0));
        System.out.println(result);

        //3
        double avgNumbers = nums.stream()
                .collect(Collectors.averagingInt(n -> n));
        System.out.println(avgNumbers);
    }
}
