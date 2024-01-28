package account;

import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;

public class Client {
    private final Map<String, Double> orderLines;

    public Client(Map<String, Double> orderLines) {
        this.orderLines = orderLines;
    }

    public String toStatement() {
        return orderLines.entrySet().stream()
                .map(entry -> formatLine(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(lineSeparator(), "", formatTotal()));
    }

    private String formatTotal() {
        return lineSeparator() + "Total : " + totalAmount() + "€";
    }

    private String formatLine(String name, Double value) {
        return name + " for " + value + "€";
    }

    public Double totalAmount() {
        return orderLines.values()
                .stream()
                .mapToDouble(x -> x)
                .sum();
    }
}

