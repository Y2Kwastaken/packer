package sh.miles.packer.units;

import lombok.Getter;

public enum WeightUnit {

    OUNCES("oz", 1 / 16.0),
    POUNDS("lb", 1.0),
    GRAMS("g", 0.00220462),
    KILOGRAMS("kg", 2.20462262185);

    @Getter
    private final String symbol;
    @Getter
    private final double conversionFactor;

    WeightUnit(String symbol, double conversionFactor) {
        this.symbol = symbol;
        this.conversionFactor = conversionFactor;
    }
}
