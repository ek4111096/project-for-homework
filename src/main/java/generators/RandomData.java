package generators;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomData {
    private RandomData() {}

    public static String getUsername() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static String getPassword() {
        return RandomStringUtils.randomAlphabetic(3).toUpperCase() +
                RandomStringUtils.randomAlphabetic(5).toLowerCase() +
                RandomStringUtils.randomNumeric(3) + "$" ;
    }

    public static String getName() {
        String firstName = RandomStringUtils.randomAlphabetic(5).toLowerCase();
        String lastName = RandomStringUtils.randomAlphabetic(8).toLowerCase();

        return capitalize(firstName) + " " + capitalize(lastName);
    }

    private static String capitalize(String value) {
        return value.substring(0, 1).toUpperCase() +
                value.substring(1);
    }
}