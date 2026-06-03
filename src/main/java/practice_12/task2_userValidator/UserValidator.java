package practice_12.task2_userValidator;

import java.util.ArrayList;
import java.util.List;

public class UserValidator<T extends User> {
    List<T> users = new ArrayList<>();

    public static boolean validationEnabled = true;

    public void validate(T user) {

        if (!validationEnabled) {
            return;
        }

        nameValidation(user.getName());
        ageValidation(user.getAge());
        emailValidate(user.getEmail());
    }

    public void nameValidation(String name) {
        if (name == null || name.isBlank() || !Character.isUpperCase(name.charAt(0)) ) {
            throw new InvalidUserException("Invalid name. The name must start with a capital letter and contain only letters.");
        }
    }

    public void ageValidation(int age) {
        if (age < 18 || age > 100) {
            throw new InvalidUserException("Invalid age. Age must be between 18 and 100.");
        }
    }

    public void emailValidate(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new InvalidUserException("Invalid email. Please enter an email in the format: example@email.com.");
        }
    }
}
