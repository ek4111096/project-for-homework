package practice_6.LinkedHashSetSolvers;

import java.util.LinkedHashSet;
import java.util.Set;

public class Main {
    public static void addUnique(LinkedHashSet<String> set, String element) {
        if (!set.contains(element)) {
            set.add(element);
        }
    }
    public static void main(String[] args) {
        //1
        Set<String> names = new LinkedHashSet<>();
        names.add("Katya");
        names.add("Sasha");
        names.add("Masha");
        names.add("Dasha");
        names.add("Olya");
        System.out.println(names);

        //2
        LinkedHashSet <String> set1 = new LinkedHashSet<>();
        String element1 = "Wow";
        String element2 = "Wooooo";
        String element3 = "Wow";
        addUnique(set1, element1);
        addUnique(set1, element2);
        addUnique(set1, element3);
        System.out.println(set1);

    }
}
