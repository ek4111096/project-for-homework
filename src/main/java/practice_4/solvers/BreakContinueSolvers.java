package practice_4.solvers;

import java.util.Scanner;

public class BreakContinueSolvers {
    public static void main(String[] args) {
        // sumNumbers();
        // printNumbers();
        // printPositive();
        comandStop();
    }
    public static void sumNumbers(){
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        while (true){
            System.out.println("Enter a number: ");
            int n = scanner.nextInt();
            if (n <= 0) {
                break;
            } else {
                sum += n;
            }
        }
        System.out.println("sum is : " + sum);
    }

    public static void printNumbers(){
        for (int i = 1; i <=20; i++) {
            if (i % 3 == 0) {
                continue;
            } else {
                System.out.println(i);
            }
        }
    }

    public static void printPositive(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int input = scanner.nextInt();
            if (input == 0) {
                break;
            } else if(input < 0) {
                continue;
            } else {
                System.out.println("Number: " + input);
            }
        }
    }

    public static void comandStop(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Enter comand: ");
            String input = scanner.nextLine();
            if (input.equals("stop")) {
                break;
            } else {
                System.out.println("Comand " + input);
            }
        }
    }
}
