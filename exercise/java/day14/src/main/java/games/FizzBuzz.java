package games;

import java.util.LinkedHashMap;
import java.util.Map;

import static games.Result.failure;
import static games.Result.fromSuccess;

public class FizzBuzz {
    public static final int MIN = 0;
    public static final int MAX = 100;
    public static final int FIZZ = 3;
    public static final int BUZZ = 5;
    public static final int FIZZBUZZ = 15;

    private static final Map<Integer, String> mapping;

    static {
        mapping = new LinkedHashMap<>();
        mapping.put(FIZZBUZZ, "FizzBuzz");
        mapping.put(FIZZ, "Fizz");
        mapping.put(BUZZ, "Buzz");
    }

    public static Result<String> convert(int input) {
        return isOutOfRange(input)
                ? failure()
                : fromSuccess(convertSafely(input));
    }

    private static String convertSafely(Integer input) {
        return mapping.entrySet()
                .stream()
                .filter(f -> is(f.getKey(), input))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(input.toString());
    }

    private static boolean is(Integer divisor, Integer input) {
        return input % divisor == 0;
    }

    private static boolean isOutOfRange(Integer input) {
        return input <= MIN || input > MAX;
    }


}
