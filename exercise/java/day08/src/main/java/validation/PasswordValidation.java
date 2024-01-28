package validation;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public class PasswordValidation {

    private static final int MINIMUM_LENGTH = 8;
    private static final String specialCharactersRegexp = "[.*#@$%&]";

    private static final List<Character> specialCharacters = Arrays.asList('.', '*', '#', '@', '$', '%', '&');

    public boolean validPassword(String password) {
        if (containAtLeast8Character(password)
                && containAtLeastOneCapitalLetter(password)
                && containAtLeastOneLowerLetter(password)
                && containAtLeastOneNumber(password)
                && containAtLeastOneSpecialCharacter(password)
                && noInvalidCharacters(password)) {
            return true;
        }
        return false;
    }

    private static boolean noInvalidCharacters(String password) {
        // This logic is duplicated
        return password
                .chars()
                .mapToObj(c -> (char) c)
                .allMatch(c -> Character.isLetter(c)
                        || Character.isDigit(c)
                        || specialCharacters.contains(c));
    }

    private boolean containAtLeastOneSpecialCharacter(String password) {
        Pattern special = Pattern.compile(specialCharactersRegexp);
        return special.matcher(password).find();
    }

    private boolean containAtLeastOneNumber(String password) {
        return password.matches(".*\\d.*");
    }

    private boolean containAtLeastOneCapitalLetter(String password) {
        return password.chars().anyMatch(c -> Character.isUpperCase(c));
    }

    private boolean containAtLeastOneLowerLetter(String password) {
        return password.chars().anyMatch(c -> Character.isLowerCase(c));
    }

    private static boolean containAtLeast8Character(String password) {
        return password.length() >= MINIMUM_LENGTH;
    }

    // SOLUTION use Predication functional interface

    public static boolean validate(String password) {
        return containAtLeast8Character(password)
                // We parameterize the atLeastOne method
                && atLeastOne(password,
                Character::isUpperCase,
                Character::isLowerCase,
                Character::isDigit,
                specialCharacters::contains
        )
                && noInvalidCharacters(password);
    }

    @SafeVarargs
    private static boolean atLeastOne(String password, Predicate<Character>... predicates) {
        return stream(predicates)
                // We apply each predicate on each character and verify that all predicate have been matched at least one
                .allMatch(predicate -> chars(password).anyMatch(predicate));
    }

    private static Stream<Character> chars(String password) {
        return password
                .chars()
                .mapToObj(c -> (char) c);
    }
}
