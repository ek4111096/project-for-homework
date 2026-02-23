package practice_4.solvers;

import java.util.Scanner;

public class ForLoopSolvers {
    public static void main(String[] args) {
        // threeDividion();
        // countSum();
        // multiplyTable();
        // checkNumber();
        outprint();
    }
    public static void threeDividion(){
        for (int i = 0; i <= 100; i++) {
            if (i % 3 == 0) {
                System.out.println(i);
            }
        }
    }

    public static void countSum() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int sum = 0;
        for (int i = 0; i <= n; i++) {
            sum = sum + i;
        }
        System.out.println(sum);
    }

    public static void multiplyTable() {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();

        for (int i = 1; i < 10; i++) {
            System.out.println(number + " * " + i + " = " + number*i);
        }
    }

    public static void checkNumber() {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        boolean isPrime = true;
        for (int i = 2; i <= number -1; i++ ) {
            if (number % i == 0) {
                isPrime = false;
                break;
            }
        }
        System.out.println("Is number prime - " + isPrime);
    }

    public static void outprint(){
        for (int i = 1; i <=10; i++) {
            System.out.println(i);
        }
    }
}
