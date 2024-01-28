package greeting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NewGreeterTest {
    @Test
    void saysHello() {
        var greeter = NewGreeter.create();

        assertThat(greeter.greet())
                .isEqualTo("Hello.");
    }

    @Test
    void saysHelloFormally() {
        var greeter = NewGreeter.create("formal");

        assertThat(greeter.greet())
                .isEqualTo("Good evening, sir.");
    }

    @Test
    void saysHelloCasually() {
        var greeter = NewGreeter.create("casual");

        assertThat(greeter.greet())
                .isEqualTo("Sup bro?");
    }

    @Test
    void saysHelloIntimately() {
        var greeter = NewGreeter.create("intimate");

        assertThat(greeter.greet())
                .isEqualTo("Hello Darling!");
    }
}
