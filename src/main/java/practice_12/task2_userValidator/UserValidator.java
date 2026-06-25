package practice_12.task2_userValidator;

public class UserValidator <T extends User> {
    public static boolean validationEnabled = true;

    public void validate(T user) {
        if (!validationEnabled) {
            return;
        }
        nameValidation(user.getName());
        ageValidation(user.getAge());
        emailValidation(user.getEmail());
    }

    public void nameValidation(String name) {
        if (name == null || name.isBlank() || !Character.isUpperCase(name.charAt(0))) {
            throw new InvalidUserException("Invalid name. The name should starts with a capital letter and contains only letters");
        }
    }

    public void ageValidation(int age) {
        if (age < 18 || age > 100) {
            throw new InvalidUserException("Invalid age. Age should be between 18 and 100");
        }
    }

    public void emailValidation(String email) {
        if (email == null || !email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            throw new InvalidUserException("Invalid email. Please enter an email in the format: example@email.com");
        }
    }
}
