package practice_10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TaskSolversTest  {
    TaskSolvers taskSolvers = new TaskSolvers();

    //1
    /**
     * Tests for isEven:
     * happy path:
     * - numbers - even
     * negative cases:
     * - numbers - odd
     * corner cases:
     * - number -> 0
     *  - number -> negative
     */

    //happy path - positive number, corner case - 0, negative case - negative number
    @ParameterizedTest
    @ValueSource(ints = {6, 0, -6})
    public void  checkNumberIsEven(int number) {
        assertTrue(taskSolvers.isEven(number), "number is even");
    }
    //negative case: numbers - odd
    @ParameterizedTest
    @ValueSource(ints = {5, 3, 1})
    public void checkNumberIsOdd(int number) {
        assertFalse(taskSolvers.isEven(number), "number is odd");
    }

    //2
    /**
     * Tests for countVowels:
     * happy path:
     * - check: "hello" -> 2
     * - check: "java" -> 2
     * - check: "AEIOU" -> 5
     * - check: "SvBbFf" -> 0
     * negative cases:
     * -
     * corner cases:
     * - "" -> 0
     * - null -> IllegalArgumentException
     */

    @ParameterizedTest
    @CsvSource({
            "hello, 2",
            "java, 2",
            "AEIOU, 5",
            "SvBbFf, 0",
            "'', 0"
    })
    public void checkCountVowels(String string, int expected) {
        int actual = taskSolvers.countVowels(string);
        assertEquals(expected, actual);
    }

    @Test
    public void checkCountVowelsWithNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskSolvers.countVowels(null);
        }, "Null string should lead to IllegalArgumentException");
    }

    //3
    /**
     * Tests for reverse:
     * happy path:
     * - check: "hello" -> "olleh"
     * - check: "java" -> "avaj"
     *
     * negative cases:
     * -
     * corner cases:
     * - "" -> ""
     * - null -> null
     */
    @ParameterizedTest
    @CsvSource({
            "hello, olleh",
            "java, avaj",
            "'',''"
    })
    public void checkReverse(String input, String expected) {
        String actualResult = taskSolvers.reverse(input);
        assertEquals(expected, actualResult);
    }

    @Test
    public void checkReverseWithNull() {
        assertNull(taskSolvers.reverse(null));
    }

    //4
    /**
     * Tests for findMax:
     * happy path:
     * - check: regular array -> [3, 5, 7, 2], 7
     * - check: negative array -> [-8, -9, -3, -5], -3
     *
     * negative cases:
     * - empty array
     * - null - NullPointerException
     * corner cases:
     * - one element in an array -> [5], 5
     */
    public static Stream<Arguments> arrayProvider() {
        return Stream.of(
                Arguments.of(new int[] {3, 5, 7, 2}, 7),
                Arguments.of(new int[] {-8, -9, -3, -5}, -3),
                Arguments.of(new int[] {5}, 5)
        );
    }

    @ParameterizedTest
    @MethodSource("arrayProvider")
    public void checkFindMax(int[] input, int expectedResult) {
        int actualResult = taskSolvers.findMax(input);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void checkFindMaxWithEmptyArray() {
        assertThrows(NoSuchElementException.class, () -> {
            taskSolvers.findMax(new int[]{});
        }, "No value should lead to NoSuchElementException");
    }
    //negative case: null -> NullPointerException
    @Test
    public void checkFindMaxWithNull() {
        assertThrows(NullPointerException.class, () -> {
            taskSolvers.findMax(null);
        }, "Null should lead to NullPointerException");
    }

    //5
    /**
     * Tests for isLeapYear:
     * happy path:
     * - check: regular years -> 2026, 2025, 2023
     * - check: leap years -> 2020, 2000, 1600
     *
     * negative cases:
     * -
     * corner cases:
     * - years are divisible by 100 but not by 400 (1900, 2100)
     */

    @ParameterizedTest
    @ValueSource(ints = {2026, 2025, 2023})
    public void checkIsNotLeapYear(int input) {
        boolean actualResult = taskSolvers.isLeapYear(input);
        assertFalse(actualResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {2020, 2000, 1600})
    public void checkIsLeapYear(int input){
        boolean actualResult = taskSolvers.isLeapYear(input);
        assertTrue(actualResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {1900, 2100})
    public void checkIsYearDivisible100Not400(int input) {
        boolean actualResult = taskSolvers.isLeapYear(input);
        assertFalse(actualResult);
    }

    //6
    /**
     * Tests for isValidEmail:
     * happy path:
     * - check: valid emails -> "test@example.com"
     * negative cases:
     * - - check: invalid emails -> "bad@.com", "no-at-symbol"
     * corner cases:
     * - null
     */
    @ParameterizedTest
    @ValueSource(strings = {"test@example.com"})
    public void checkIsValidEmail(String input) {
        boolean actualResult = taskSolvers.isValidEmail(input);
        assertTrue(actualResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {"bad@.com", "no-at-symbol"})
    public void checkIsNotValidEmail(String input) {
        boolean actualResult = taskSolvers.isValidEmail(input);
        assertFalse(actualResult);
    }

    @Test
    public void checkIsValidEmailWithNull() {
        assertFalse(taskSolvers.isValidEmail(null));
    }

    //7
    /**
     * Tests for factorial:
     * happy path:
     * - check: 0! -> 1
     * - check: small numbers -> (1!, 5!, 7!
     * negative cases:
     * - - check: negative numbers -> IllegalArgumentException
     * corner cases:
     * -
     */
    public static Stream<Arguments> numberProvider() {
        return Stream.of(
                Arguments.of(0, 1),
                Arguments.of(1, 1),
                Arguments.of(5, 120),
                Arguments.of(7, 5040)
        );
    }

    @ParameterizedTest
    @MethodSource("numberProvider")
    public void checkFactorial(int input, int expectedResult) {
        int actualResult = taskSolvers.factorial(input);
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, - 2, -100})
    public void checkFactorialWithNegativeNumbers(int input) {
        assertThrows(IllegalArgumentException.class, () -> {
            taskSolvers.factorial(input);
        }, "Negative numbers should lead to IllegalArgumentException");
    }

    //8
    /**
     * Tests for findSecondMax:
     * happy path:
     * - check: regular arrays
     * - check: array with identical numbers
     * negative cases:
     * - one element in array -> IllegalArgumentException
     * invalid array with duplicates -> NoSuchElementException
     */
     public static Stream<Arguments> arrayProviderForFindSecondMax() {
         return Stream.of(
                 Arguments.of(new int[] {5, 7, 58, 23, -6}, 23),
                 Arguments.of(new int[] {69, 35, 11}, 35),
                 Arguments.of(new int[] {5, 7, 23, -6}, 7)
         );
     }
     @ParameterizedTest
     @MethodSource("arrayProviderForFindSecondMax")
     public void checkFindSecondMax(int[] ints, int expectedResult) {
         int actualResult = taskSolvers.findSecondMax(ints);
         assertEquals(expectedResult, actualResult);
     }

     @Test
     public void checkFindSecondMaxWithOneElementInArray() {
         assertThrows(IllegalArgumentException.class, () -> {
             taskSolvers.findSecondMax(new int[] {1});
         });
     }
    //negative case: invalid array with duplicates -> NoSuchElementException
     @Test
     public void checkFindSecondMaxWithInvalidArray() {
         assertThrows(NoSuchElementException.class, () -> {
             taskSolvers.findSecondMax(new int[] {7, 7, 7, 8});
         }, "Invalid array should lead to NoSuchElementException");
    }

     //9
    /**
     * Tests for countWords:
     * happy path:
     * - check: an empty string
     * - check: a string with multiple spaces
     * negative cases:
     * - null -> NullPointerException
     */
    public static Stream<Arguments> stringProvider() {
        return Stream.of(
                Arguments.of("", 0),
                Arguments.of("hello java test", 3)
        );
    }
    @ParameterizedTest
    @MethodSource("stringProvider")
    public  void  checkCountWords(String input, int expectedResult) {
        int actualResult = taskSolvers.countWords(input);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void checkCountWordsWithNull() {
        assertThrows(NullPointerException.class, () -> {
            taskSolvers.countWords(null);
        });
    }

    //10
    /**
     * Tests for isValidPhoneNumber:
     * happy path:
     * - check: valid number
     * - check: invalid number
     */
    @Test
    public void checkIsValidPhoneNumber() {
        assertTrue(taskSolvers.isValidPhoneNumber("+1 1234567890"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345", "invalid"})
    public void checkIsNotValidPhoneNumber(String input) {
        assertFalse(taskSolvers.isValidPhoneNumber(input));
    }




}

