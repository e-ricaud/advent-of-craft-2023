package diamond;

import io.vavr.control.Option;

import static io.vavr.API.Some;

public class Diamond {
    public static Option<String> print(char endCharacter) {
        return isValidCharacter(endCharacter)
                ? Some("A")
                : Option.none();
    }

    private static boolean isValidCharacter(char endCharacter) {
        return endCharacter >= 'A' && endCharacter <= 'Z';
    }
}