package practice_7;

import java.util.List;

public class Main {
    public static void sum(List<? extends Number> list) {
        double sum = 0.0;
        for (Number number : list) {
            sum += number.doubleValue();
        }
    }
    public static void addNumbers(List<? super Number> list, Integer n) {
        list.add(n);
        for (Object number : list) {
            System.out.println(number);
        }
    }
}
