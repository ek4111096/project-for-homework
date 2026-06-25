package practice_12;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import practice_12.task2_userValidator.Employee;
import practice_12.task2_userValidator.InvalidUserException;
import practice_12.task2_userValidator.User;
import practice_12.task2_userValidator.UserValidator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserValidatorTest {
    /**
     * метод проверки имени
     * happy path: valid name -> Sasha
     * corner case:
     * S
     * negative cases:
     * null, katya, "", " "
     */

    UserValidator<User> validator = new UserValidator<>();
    Employee employee = new Employee("katya", 18, "katya@email.com");
    @ParameterizedTest
    @ValueSource(strings = {
            "Sasha",
            "Pa",
            "K"
    })
    public void nameValidationWithValidNames(String s) {
        assertDoesNotThrow(() -> validator.nameValidation(s));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "katya",
            " ",
            "",
            "123"
    })
    public void nameValidationWithInvalidNames(String s) {
        assertThrows(InvalidUserException.class, () -> {
            validator.nameValidation(s);
        });
    }

    @Test
    public void nameValidationWithNull() {
        assertThrows(InvalidUserException.class, () -> {
            validator.nameValidation(null);
        });
    }

    /**
     * метод проверки age
     * happy path: 18, 58, 100
     * negative cases: 17, 101, 0, -5
     * corner case: null
     */

    @ParameterizedTest
    @ValueSource(ints = {
            18,
            58,
            100
    })
    public void ageValidationWithValidValue(int age) {
        assertDoesNotThrow(() -> validator.ageValidation(age));
    }
    @ParameterizedTest
    @ValueSource(ints = {
            17,
            101,
            0,
            -5
    })
    public void ageValidationWithInvalidValue(int age) {
        assertThrows(InvalidUserException.class, () ->
                validator.ageValidation(age));
    }

    /**
     * метод проверки email
     * happy path: sasha@email.com, sasha123@email.ru, sasha_123@email.com
     * negative cases: sasha@, sasha@email, sasha, @email.ru, sashaemail.com
     */

    @ParameterizedTest
    @ValueSource(strings = {
            "sasha@email.com",
            "sasha123@email.ru",
            "sasha_123@email.com"
    })
    public void emailValidationWithValidValue(String email) {
        assertDoesNotThrow(() -> validator.emailValidation(email));
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "sasha@",
            "sasha@email",
            "sasha",
            "@email.ru",
            "sashaemail.com"
    })
    public void emailValidationWithInvalidValue(String email) {
        assertThrows(InvalidUserException.class, () ->
                validator.emailValidation(email));
    }

    /**
     * проверки флага
     */

    @Test
    public void validationFlagOff() {
        UserValidator.validationEnabled = false;
        assertDoesNotThrow(() -> validator.validate(employee));
    }

    @Test
    public void validationFlagOn() {
        UserValidator.validationEnabled = true;
        assertThrows(InvalidUserException.class, () -> {
            validator.validate(employee);
        });
    }
}
