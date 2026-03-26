package practice_6.HashSetSolvers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static Set<String> noDuplicates(List<String> input) {
        Set<String> result = new HashSet<>();
        result.addAll(input);
        return result;
    }
    public static void main(String[] args) {
        //1
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            set.add(i+1);
        }
        System.out.println(set);

        //2
        Set<Integer> set1 = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            set1.add(i+1);
        }
        System.out.println(set1);

        if (set1.contains(10)) {
            System.out.println(true);
        } else System.out.println(false);

        //3
        List<String> list = Arrays.asList("apple", "banana", "apple", "orange");
        Set<String> result = noDuplicates(list);
        System.out.println(result);

        //4
        Set<String> names = new HashSet<>();
        names.add("Anna");
        names.add("Katya");
        names.add("Sasha");
        names.add("Anna");
        System.out.println(names.contains("Lena")); //redundant check removed
        System.out.println(names);

    }
}
