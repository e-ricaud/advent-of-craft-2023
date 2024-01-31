import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static diamond.Diamond.print;
import static io.vavr.test.Gen.choose;
import static io.vavr.test.Property.def;
import static java.lang.System.lineSeparator;

class DiamondTests {
    @Test
    void fail_for_invalid_end_character() {
        var notALetterGenerator = choose(' ', '~')
                .filter(x -> !Character.isLetter(x))
                .arbitrary();

        def("None for invalid end characters")
                .forAll(notALetterGenerator)
                .suchThat(endCharacter -> print(endCharacter).isEmpty())
                // check and satisfied on each property
                .check()
                .assertIsSatisfied();
    }

    @Test
    void be_horizontally_symmetric() {
        var upperLetterGenerator = choose('A', 'Z').arbitrary();

        def("Horizontally symmetric for valid end characters")
                // Extract this
                .forAll(upperLetterGenerator)
                .suchThat(endCharacter -> {
                    var diamond = print(endCharacter).get();
                    // retrieving the lines
                    var lines = List.of(diamond.split(lineSeparator()));
                    var reversedDiamond = lines.reverse();

                    return lines.equals(reversedDiamond);

                })
                .check()
                .assertIsSatisfied();
    }

    @Test
    void be_a_square() {
        var upperLetterGenerator = choose('A', 'Z').arbitrary();

        def("A square for valid end characters")
                .forAll(upperLetterGenerator)
                .suchThat(endCharacter -> {
                    var diamond = print(endCharacter).get().split(lineSeparator());
                    return List.of(diamond).forAll(line -> line.length() == diamond.length);
                })
                .check()
                .assertIsSatisfied();
    }
}
