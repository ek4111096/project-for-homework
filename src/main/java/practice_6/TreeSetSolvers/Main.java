package practice_6.TreeSetSolvers;

import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void noDuplicates(TreeSet<Integer> set, Integer element) {
        set.add(element);
    }
    public static void main(String[] args) {
        //1
        Set<Integer> set = new TreeSet<>();
        set.add(5);
        set.add(20);
        set.add(15);
        set.add(100);
        set.add(35);
        System.out.println(set);

        //2
        TreeSet<Integer> set1 = new TreeSet<>();
        Integer element = 2;
        Integer element1 = 60;
        Integer element2 = 15;
        Integer element3 = 2;
        noDuplicates(set1, element);
        noDuplicates(set1, element1);
        noDuplicates(set1, element2);
        noDuplicates(set1, element3);
        System.out.println(set1);

        //3
        TreeSet<Integer> set2 = new TreeSet<>();
        set2.add(5);
        set2.add(3);
        set2.add(7);
        set2.add(9);
        set2.add(10);
        System.out.println(set2);
        if (set2.higher(8) != null) {                             //0->null
            System.out.println(set2.higher(8));
        }
        if (set2.lower(8) != null) {                               //0->null
            System.out.println(set2.lower(8));
        }


    }
}
