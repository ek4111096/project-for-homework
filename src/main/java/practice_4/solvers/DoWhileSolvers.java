package practice_4.solvers;

import java.util.Scanner;

public class DoWhileSolvers {
    public static void main(String[] args) {
        // requestPositive();
        // checkPassword();
        // outprintNumbers();
        // finishProgram();
        countNumbers();
    }
    public static void requestPositive(){
        Scanner scanner = new Scanner(System.in);
        int number = 0;
        do {
            System.out.println("Enter a number");
            number = scanner.nextInt();
        } while (number <= 0);
    }

    public static void checkPassword(){
        Scanner scanner = new Scanner(System.in);
        String pass = "admin";
        String input = "";
        do {
            System.out.println("Enter password: ");
            input = scanner.nextLine();
        } while (!input.equals(pass));
        System.out.println("Correct password");
    }

    public static void outprintNumbers(){
        int count = 1;
        do {
            System.out.println(count);
            count++;
        } while (count <=10);
    }

    public static void finishProgram(){
        Scanner scanner = new Scanner(System.in);
        String comand = "exit";
        String input = "";
        do {
            System.out.println("Enter comand: ");
            input = scanner.nextLine();
        } while (!input.equals(comand));
        System.out.println("Finish");
    }

    public static void countNumbers(){
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        int n = scanner.nextInt();
        if (n == 0) {
            System.out.println("1 number");
        } else {
            do {
                n = n / 10;
                count++;
            } while (n > 0);
        }
        System.out.println("Count of numbers: " + count);
    }
}
