package practice_7.genericsSolvers;

import java.util.ArrayList;
import java.util.List;

public class NumberBox<T extends Number> {
    private List<T> numbers = new ArrayList<>();

    // добавление числа
    public void add(T number) {
        numbers.add(number);
    }

    // вычисление суммы
    public double getSum() {
        double sum = 0.0;

        for (T number : numbers) {
            sum += number.doubleValue();
        }

        return sum;
    }

}
