package practice_4.solvers;

import java.util.Scanner;

public class SwitchSolvers {
    public static void main(String[] args) {
        // dayOfWeek();
        // ticketPrice();
        // numbersToLetters();
        // textOperations();
        simleCalculator();
    }
    public static String dayOfWeek(){
        Scanner scanner = new Scanner(System.in);
        int day = scanner.nextInt();
        String dayOfWeek = "";

        switch (day) {
            case 1:
                dayOfWeek = "Monday";
                System.out.println(day + " - " + dayOfWeek);
                break;
            case 2:
                dayOfWeek = "Tuesday";
                System.out.println(day + " - " + dayOfWeek);
                break;
            case 3:
                dayOfWeek = "Wednesday";
                System.out.println(day + " - " + dayOfWeek);
                break;
            case 4:
                dayOfWeek = "Thursaday";
                System.out.println(day + " - " + dayOfWeek);
                break;
            case 5:
                dayOfWeek = "Friday";
                System.out.println(day + " - " + dayOfWeek);
                break;
            case 6:
                dayOfWeek = "Saturday";
                System.out.println(day + " - " + dayOfWeek);
                break;
            case 7:
                dayOfWeek = "Sunday";
                System.out.println(day + " - " + dayOfWeek);
            default:
                dayOfWeek = "Unknown day";
                System.out.println(day + " - " + dayOfWeek);
        }
        return dayOfWeek;

    }

    public static String ticketPrice(){
        Scanner scanner = new Scanner(System.in);
        int day = scanner.nextInt();
        String price = "";

        switch (day){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                price = "300 rub";
                System.out.println(price);
                break;
            case 6:
            case 7:
                price = "450 rub";
                System.out.println(price);
                break;
            default:
                System.out.println("Unknown  day");
        }
        return price;
    }

    static public String numbersToLetters(){
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        String letterGrade = "";

        if (number >= 90 && number <= 100) {
            letterGrade = "A";
            System.out.println("90–100 — A");
        } else if (number >= 80 && number <= 89) {
            letterGrade = "B";
            System.out.println("80–89 — B");
        } else if (number >= 70 && number <= 79) {
            letterGrade = "C";
            System.out.println("70–79 — C");
        } else if (number >= 60 && number <= 69) {
            letterGrade = "D";
            System.out.println("60–69 — D");
        } else if (number >= 0 && number <= 59) {
            letterGrade = "F";
            System.out.println("lower 60 — F");
        } else {
            System.out.println("Wrong number");
        }
        return letterGrade;
    }

    public static String textOperations() {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        String result = "";

        switch (command){
            case "start":
                result = "System started";
                System.out.println(result);
                break;
            case "stop":
                result = "System stoped";
                System.out.println(result);
                break;
            case "restart":
                result = "System restarted";
                System.out.println(result);
                break;
            case "status":
                result = "System read status";
                System.out.println(result);
                break;
            default:
                System.out.println("Enter correct command");
        }
        return result;
    }

    public static int simleCalculator(){
        Scanner scanner = new Scanner(System.in);
        int num1 = scanner.nextInt();
        int num2 = scanner.nextInt();
        String operator = scanner.next();
        int result = 0;

        switch (operator) {
            case "+":
               result = num1 + num2;
               System.out.println("Result is sum = " + result);
               break;
            case "-":
                result = num1 - num2;
                System.out.println("Result is = " + result);
                break;
            case "*":
                result = num1 * num2;
                System.out.println("Result is multiply = " + result);
                break;
            case "/":
                if (num2 == 0) {
                    System.out.println("Error zero dividion");
                } else {
                    result = num1 / num2;
                    System.out.println("Result is dividion = " + result);
                    break;
                }
            default:
                System.out.println("Unknown operator");
        }
        return result;
    }

}
