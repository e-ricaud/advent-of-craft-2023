package greeting;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Greeter {
    String formality;

    public enum Formality {
        formal, casual, intimate;
    }

    private static final Map<Formality, String> mapping;

    static {
        mapping = new LinkedHashMap<>();
        mapping.put(Formality.formal, "Good evening, sir.");
        mapping.put(Formality.casual, "Sup bro?");
        mapping.put(Formality.intimate, "Hello Darling!");
    }

    public String greet() {
        if (this.formality == null) {
            return "Hello.";
        }

        return Optional.ofNullable(mapping.get(Formality.valueOf(formality))).orElse("Hello");

    }

    public void setFormality(String formality) {
        this.formality = formality;
    }
}
