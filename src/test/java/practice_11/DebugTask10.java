package practice_11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DebugTask10 {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(Arrays.asList("Alice", "Bob", "Charlie"));
        List<String> namesCopy = new ArrayList<>(names);
        for (String name : namesCopy) {
            if (name.startsWith("A")) {
                names.remove(name);
                System.out.println(names);
            }
        }
    }
}
