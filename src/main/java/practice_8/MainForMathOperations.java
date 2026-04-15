package practice_8;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class MainForMathOperations {
    public static void main(String[] args) {
        MathOperations add = (x, y) -> x + y;
        MathOperations substract = (x, y) -> x - y;
        MathOperations divide = (x, y) -> x / y;
        MathOperations multiply = (x, y) -> x * y;

        System.out.println(add.apply(2,3));
        System.out.println(substract.apply(5,3));
        System.out.println(divide.apply(9,3));
        System.out.println(multiply.apply(3,3));

        //2
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from anonymous class!");
            }
        };
        r.run();

        //3
        Predicate<Integer> isEven = x -> x % 2 == 0;
        System.out.println(isEven.test(7));
        System.out.println(isEven.test(20));

        //4
        Function<String, Integer> getLength = string -> string.length();
        System.out.println(getLength.apply("lena"));

        //5
        Consumer<String> printer = message -> System.out.println("Message: " + message);
        printer.accept("Hi everybody");
    }
}
