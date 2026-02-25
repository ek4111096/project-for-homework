package practice_4.solvers;

import java.util.Scanner;

public class BreakContinueSolvers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //added 1 scanner for the whole class
        sumNumbers(scanner);
        printNumbers();
        printPositive(scanner);
        commandStop(scanner);
    }
    public static void sumNumbers(Scanner scanner){
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

    public static void printPositive(Scanner scanner){
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

    public static void commandStop(Scanner scanner){
        while (true){
            System.out.println("Enter command: ");
            String input = scanner.nextLine();
            if (input.equals("stop")) {
                break;
            } else {
                System.out.println("Command " + input);
            }
        }
    }
}
