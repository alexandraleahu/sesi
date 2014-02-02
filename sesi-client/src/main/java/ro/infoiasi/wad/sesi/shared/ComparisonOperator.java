package ro.infoiasi.wad.sesi.shared;

import ro.infoiasi.wad.sesi.core.util.HasDescription;

import java.util.HashMap;
import java.util.Map;

public enum ComparisonOperator implements HasDescription {

    eq("="),
    ge(">="),
    gt(">"),
    le("<="),
    lt("<");

    private static final Map<String, ComparisonOperator> MAPPINGS = buildMappingsFromSymbol();

    private static Map<String, ComparisonOperator> buildMappingsFromSymbol() {

        Map<String, ComparisonOperator> mappings = new HashMap<String, ComparisonOperator>();
        for (ComparisonOperator operator : ComparisonOperator.values()) {
            mappings.put(operator.getDescription(), operator);
        }
        return mappings;
    }
    public static ComparisonOperator fromDescription(String description) {
        return MAPPINGS.get(description);
    }
    private final String symbol;

    ComparisonOperator(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return symbol;
    }

}
