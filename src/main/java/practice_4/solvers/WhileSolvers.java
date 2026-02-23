package practice_4.solvers;

import java.util.Scanner;

public class WhileSolvers {
    public static void main(String[] args) {
        // countFactorial();
        // outprintNumbers();
        backwardCont();
    }
    public static void countFactorial(){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int result = 1;
        int i = 1;
        while (i <= n) {
            result = result * i;
            i++;
        }
        System.out.println(result);
    }

    public static void outprintNumbers(){
        Scanner scanner = new Scanner(System.in);
        int n  = scanner.nextInt();
        int i = 1;
        while (i <= n) {
            if (i % 2 == 0) {
                System.out.println(i);
            }
            i++;
        }
    }

    public static void backwardCont(){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        if (n > 0) {
            while (n > 0) {
                System.out.println(n);
                n--;
            }
        } else {
            System.out.println("Write positive number");
        }


    }
}
