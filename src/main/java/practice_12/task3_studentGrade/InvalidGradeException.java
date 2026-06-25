package practice_12.task3_studentGrade;

import practice_12.task2_userValidator.InvalidUserException;

public class InvalidGradeException extends RuntimeException {
    public InvalidGradeException(String message) {
        super(message);
    }
}
