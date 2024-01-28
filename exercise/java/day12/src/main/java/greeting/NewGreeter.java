package greeting;

public interface NewGreeter {
    static NewGreeter create() {
        return new DefaultGreeter();
    }

    static NewGreeter create(String formality) {
        return switch (formality) {
            case "casual" -> new Casual();
            case "intimate" -> new Intimate();
            default -> new Formal();
        };
    }

    default String greet() {
        return "Hello.";
    }

    class DefaultGreeter implements NewGreeter {
    }

    class Formal implements NewGreeter {
        @Override
        public String greet() {
            return "Good evening, sir.";
        }
    }

    class Casual implements NewGreeter {
        @Override
        public String greet() {
            return "Sup bro?";
        }
    }

    class Intimate implements NewGreeter {
        @Override
        public String greet() {
            return "Hello Darling!";
        }
    }
}