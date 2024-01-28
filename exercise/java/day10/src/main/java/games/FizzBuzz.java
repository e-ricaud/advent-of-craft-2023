package games;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class FizzBuzz {
    public static final int MIN = 0;
    public static final int MAX = 100;
    public static final int FIZZ = 3;
    public static final int BUZZ = 5;
    public static final int FIZZBUZZ = 15;

    public static final boolean DEFAULT = true;

    private static final Map<Predicate<Integer>, Function<Integer, String>> mapping;

    static {
        mapping = new LinkedHashMap<>();
        mapping.put(i -> is(FIZZBUZZ, i), i -> "FizzBuzz");
        mapping.put(i -> is(FIZZ, i), i -> "Fizz");
        mapping.put(i -> is(BUZZ, i), i -> "Buzz");
        // Default value for numbers in range
        mapping.put(i -> DEFAULT, Object::toString);
    }

    private FizzBuzz() {
    }

    public static String convert(Integer input) throws OutOfRangeException {
        return mapping.entrySet()
                .stream()
                // use filter function to check if the input is valid
                .filter(f -> !isOutOfRange(input))
                // We start by filtering the stream based on wether the Predicate is matching the value or not
                .filter(f -> f.getKey().test(input))
                // We keep only the first entry
                .findFirst()
                // We take the associated value (Function<Integer, String>)
                .map(v -> v.getValue().apply(input))
                // We throw the Exception if no match
                .orElseThrow(OutOfRangeException::new);
    }

    private static boolean is(Integer divisor, Integer input) {
        return input % divisor == 0;
    }

    private static boolean isOutOfRange(Integer input) {
        return input <= MIN || input > MAX;
    }
}
