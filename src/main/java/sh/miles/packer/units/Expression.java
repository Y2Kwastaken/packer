package sh.miles.packer.units;

import java.util.function.BiPredicate;

public enum Expression {
    
    GREATER_THAN((a, b) -> a.doubleValue() > b.doubleValue()),
    LESS_THAN((a, b) -> a.doubleValue() < b.doubleValue()),
    EQUALS((a, b) -> a.doubleValue() == b.doubleValue()),
    NOT_EQUALS((a, b) -> a.doubleValue() != b.doubleValue()),
    GREATER_THAN_OR_EQUALS((a, b) -> a.doubleValue() >= b.doubleValue()),
    LESS_THAN_OR_EQUALS((a, b) -> a.doubleValue() <= b.doubleValue());
    
    private final BiPredicate<Number, Number> function;
    Expression(BiPredicate<Number, Number> function) {
        this.function = function;
    }

    public boolean test(Number a, Number b) {
        return function.test(a, b);
    }

}
