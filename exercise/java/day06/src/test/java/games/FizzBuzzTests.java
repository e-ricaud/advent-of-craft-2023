package games;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FizzBuzzTests {

    // Add Test Parameterize pour eviter la duplication de tests similaire
    public static Stream<Arguments> fizzBuzzTestParams() {
        return Stream.of(
                Arguments.of(1, "1"),
                Arguments.of(67, "67"),
                Arguments.of(82, "82"),
                Arguments.of(3, "Fizz"),
                Arguments.of(66, "Fizz"),
                Arguments.of(99, "Fizz"),
                Arguments.of(5, "Buzz"),
                Arguments.of(50, "Buzz"),
                Arguments.of(85, "Buzz"),
                Arguments.of(15, "FizzBuzz"),
                Arguments.of(30, "FizzBuzz"),
                Arguments.of(45, "FizzBuzz")
        );
    }

    @ParameterizedTest
    @MethodSource("fizzBuzzTestParams")
    void returns_the_given_number(int givenNumber, String expectedResult) throws OutOfRangeException {
        assertThat(FizzBuzz.convert(givenNumber))
                .isEqualTo(expectedResult);
    }

    public static Stream<Arguments> invalidInputParams() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(101),
                Arguments.of(-1)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidInputParams")
    void throw_exception_when_input_invalid(int givenNumber) throws OutOfRangeException {
        assertThatThrownBy(() -> FizzBuzz.convert(givenNumber))
                .isInstanceOf(OutOfRangeException.class);
    }
}