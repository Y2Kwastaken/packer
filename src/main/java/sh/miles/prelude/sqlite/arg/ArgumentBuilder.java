package sh.miles.prelude.sqlite.arg;

import java.util.ArrayList;
import java.util.List;

/**
 * A SQLiteArgumentBuilder is a builder for SQLite arguments.
 */
public class ArgumentBuilder {

    private final List<Argument> arguments;

    private ArgumentBuilder() {
        this.arguments = new ArrayList<>();
    }

    public ArgumentBuilder addArg(Argument argument) {
        this.arguments.add(argument);
        return this;
    }

    public Argument[] build() {
        return this.arguments.toArray(new Argument[0]);
    }

    public ValuedArgument[] buildValued() {
        return this.arguments.stream().filter(arg -> arg instanceof ValuedArgument)
                .map(arg -> (ValuedArgument) arg).toArray(ValuedArgument[]::new);
    }

    public static final ArgumentBuilder builder() {
        return new ArgumentBuilder();
    }

}
