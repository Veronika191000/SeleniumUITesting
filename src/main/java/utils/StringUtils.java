package utils;

public class StringUtils {

    public static String generateRandomValue() {
        return String.valueOf(Math.floor(Math.random() * (10000 - 10 + 1) + 10));
    }

}
