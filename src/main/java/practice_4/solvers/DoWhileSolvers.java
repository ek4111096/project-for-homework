package practice_4.solvers;

import java.util.Scanner;

public class DoWhileSolvers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); //added 1 scanner for the whole class
        // requestPositive(scanner);
        // checkPassword(scanner);
        // outprintNumbers(scanner);
        // finishProgram(scanner);
        countNumbers(scanner);
    }
    public static void requestPositive(Scanner scanner){
        int number = 0;
        do {
            System.out.println("Enter a number");
            number = scanner.nextInt();
        } while (number <= 0);
    }

    public static void checkPassword(Scanner scanner){
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

    public static void finishProgram(Scanner scanner){
        String comand = "exit";
        String input = "";
        do {
            System.out.println("Enter command: ");
            input = scanner.nextLine();
        } while (!input.equals(comand));
        System.out.println("Finish");
    }

    public static void countNumbers(Scanner scanner){
        int count = 0;
        int n = scanner.nextInt();
        if (n == 0) {
            System.out.println("1 number");
        } else {
            do {
                n = n / 10;
                count++;
            } while (n > 0);
            System.out.println("Count of numbers: " + count); //moved the printout to the body of else branch
        }
    }
}
