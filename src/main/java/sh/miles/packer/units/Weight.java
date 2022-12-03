package sh.miles.packer.units;

import java.io.Serializable;

import lombok.Getter;
import lombok.NonNull;

public class Weight implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    private final double value;
    @Getter
    private final WeightUnit unit;

    public Weight(@NonNull WeightUnit unit, double value) {
        this.unit = unit;
        this.value = value;
    }

    public Weight convertTo(@NonNull WeightUnit unit) {
        if (this.unit == unit) {
            return this;
        }
        return new Weight(unit, value * this.unit.getConversionFactor() / unit.getConversionFactor());
    }

    public Weight add(final double value) {
        return new Weight(unit, this.value + value);
    }

    public Weight add(@NonNull final Weight weight) {
        return add(weight.convertTo(unit).value);
    }

    public Weight subtract(final double value) {
        return new Weight(unit, this.value - value);
    }

    public Weight subtract(@NonNull final Weight weight) {
        return subtract(weight.convertTo(unit).value);
    }

    public Weight multiply(final double value) {
        return new Weight(unit, this.value * value);
    }

    public Weight multiply(@NonNull final Weight weight) {
        return multiply(weight.convertTo(unit).value);
    }

    public Weight divide(final double value) {
        return new Weight(unit, this.value / value);
    }

    public Weight divide(@NonNull final Weight weight) {
        return divide(weight.convertTo(unit).value);
    }

    public boolean compare(@NonNull final Expression expression, @NonNull final Weight weight) {
        return expression.test(value, weight.convertTo(unit).value);
    }

    public boolean compare(final Expression expression, final double value) {
        return expression.test(this.value, value);
    }

    @Override
    public String toString() {
        return String.format("%s{%s %s}", getClass().getSimpleName(), value, unit.getSymbol());
    }
}
