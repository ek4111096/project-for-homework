package practice_7.exceptionSolvers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        //1
        try {
            FileReader file = new FileReader("data.txt");
            System.out.println("File opened succecessfully");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        //2
        calc(4, 0);

        //3
        try {
            checkAge(-50);
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
        }
        //4
        validateEmail("11111@rrrr.ru");
    }
    //2
    public static void calc(int a, int b) {
        int result = 0;
        try {
            result = a / b;
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println("Zero divdsion is prohibited");
        }
    }
    public static void checkAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 150) {
            throw new InvalidAgeException("Invalid age: " + age);
        }
        System.out.println("Age is valid");
    }
    //4
    public static void validateEmail(String string) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.\\w+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);

        if (!matcher.matches()) {
            throw new InvalidEmailException("Invalid email " + string);
        }
        System.out.println("Email valid " + string);
    }
}
