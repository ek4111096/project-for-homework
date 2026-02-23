package practice_4.solvers;

import java.util.Scanner;

public class IfElseSolvers {
    public static void main(String[] args) {
        // signOfNumber();
        // maxNumber();
        // grade();
        // checkParity();
        // discount();
        testResult();

    }
    public static void signOfNumber(){
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        if (number > 0) {
            System.out.println("Number is positive");
        } else if (number < 0) {
            System.out.println("Number is negative");
        } else {
            System.out.println("Number = 0");
        }
    }

    public static int maxNumber(){
        Scanner scanner = new Scanner(System.in);
        int number1 = scanner.nextInt();
        int number2 = scanner.nextInt();
        int max = Math.max(number1, number2);
        System.out.println(max);
        return max;

    }

    public static String grade() {
        Scanner scanner = new Scanner(System.in);
        int gradeNumber = scanner.nextInt();
        String grade = "";
        if (gradeNumber == 5) {
            grade = "Great";
        } else if (gradeNumber == 4) {
            grade = "Good";
        } else if (gradeNumber == 3) {
            grade = "Satisfied";
        } else if (gradeNumber == 2 || gradeNumber == 1) {
            grade = "Unsatisfied";
        } else {
            System.out.println("You enteres wrong grade");
        }
        System.out.println(grade);
        return grade;
    }

    public static String checkParity() {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        String ifParity;
        if (number % 2 == 0) {
            ifParity = "Parity";
        } else {
            ifParity = "Unparity";
        }
        System.out.println(ifParity);
        return ifParity;
    }

    public static String discount() {
        Scanner scanner = new Scanner(System.in);
        int age = scanner.nextInt();
        String discount;
        if (age < 18) {
            discount = "25%";
        } else if (age >=65) {
            discount = "30%";
        } else {
            discount = "No discount";
        }
        System.out.println(discount);
        return discount;
    }

    public static String testResult(){
        Scanner scanner = new Scanner(System.in);
        int grade = scanner.nextInt();
        String result = "";
        if (grade >= 90 && grade <= 100) {
            result = "Perfect";
        } else if (grade >= 75 && grade <= 89) {
            result = "Good";
        } else if (grade >= 60 && grade <= 74) {
            result = "Satisfied";
        } else if (grade >= 0 && grade <= 59) {
            result = "Unsatisfied";
        } else {
            System.out.println("Enter correct grade");
        }
        System.out.println(result);
        return result;

    }

}
