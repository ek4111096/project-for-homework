import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main1 {


    public static void main(String[] args) {
        /*
        проаерка палиндрома

        Примеры: "abba" -> true, abc -> false, dd Aa

        поддерживаются ли пробелы?
        могут ли быть большие буквы?

        Алгоритм:
        1. Удаляю пробелы
        2. Привожу к нижнему регистру
        3. Читаю с одной стороны, запоминаю
        4. Читаю с другой стороны, запоминаю
        5. Если строки равны, то палиндром
        6. Протестировать через мэйн

        //2
        Сумма всех чисел в массиве

        Примеры: 123, 0123

        могут ли быть отрицательные числа?

        Алгоритм:
        1. На вход принимаю массив
        2. Создаю переменную для суммы
        3. Проверить на null и пустой массив
        4. Прохожусь по всему массиву и прибавляю каждый элемент к сумме

        //3
        Найти МАКСИМУМ в массиве
        Прмеры: 1234, 6745

        ?
        здесь нечего особо спрашивать, даже если отрицательные - они проверятся

        Алгоритм:
        1. Прверяю массив на null и что он не пустой
        2. Первый элемент массива инициализируем как максимум
        3. Все элементы массива сравниваю с первым, и перезаписываю если есть больше.
        4. Возвращаю макисмум

        //4
        !!!ВЫУЧИТЬ РЕШЕНИЕ!!!
        Проверка на ПРОСТОЕ число(делится на 1 и на себя)

        ??
        нет вопросов

        0 и 1 не явл простыми числами

        Алгоритм решения:
        1. Проверяю делимость от 2 до квадратного корня



        //5
        Факториал числа n — это произведение всех целых чисел от 1 до n.


        //6
        Подсчёт гласных

        Алгоритм
        Пройти по строке и проверять каждый символ.

         */

        System.out.println(isPalindrom("abba"));
        System.out.println(isPrime(17));




    }

    public static boolean isPalindrom(String str) {
        String clean = str.replaceAll("\\s+", "").toLowerCase();
        return new StringBuilder(clean).reverse().toString().equals(clean);
    }

    public static int arrSum(int arr[]) {
        int res = 0;
        if (arr != null) {
            for (int number : arr) {
                res += number;
            }
        } return res;
    }

    public static int findMax(int arr[]) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException();
        }
        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        } return max;
    }

    //4 ВЫУЧИТЬ
    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i*i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    //5 ВЫУЧИТЬ
    public static int factorial(int n) {
        int res = 1;
        for (int i = 1; i <= n; i++) {
            res *= i;
        }
        return res;
    }

    //6 ВЫУЧИТЬ
    public static int countVowels(String s) {
        int count = 0;
        for (char c : s.toLowerCase().toCharArray()) {
            if ("aeiouаеёиоуыэюя".indexOf(c) >= 0) count++;
        } return count;
    }

    //7 ВЫУЧИТЬ
    //ВТОРОЙ МАКСИМУМ В МАССИВЕ
    public static int secondMax(int arr[]) {
        int max = Integer.MIN_VALUE, second = Integer.MIN_VALUE;
        for (int n : arr) {
            if (n > max) {
                second = max;
                max = n;
            } else if (n > second && n != max) {
                second = n;
            }
        }
        return second;
    }

    //8 АНАГРАММЫ ВЫУЧИТЬ
    //Алгоритм: привести строки массиву символов-> отстортировать -> сравнить

    public static boolean isAnagram(String a, String b) {
        char[] ca = a.toCharArray(), cb = b.toCharArray();
        Arrays.sort(ca);
        Arrays.sort(cb);
        return Arrays.equals(ca,cb);
    }

}
