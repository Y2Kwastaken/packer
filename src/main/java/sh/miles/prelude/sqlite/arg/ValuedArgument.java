package sh.miles.prelude.sqlite.arg;

import sh.miles.prelude.sqlite.type.SQLiteType;

/**
 * A ValuedSQLiteArgument is a SQLite argument that contains vital information
 */
public class ValuedArgument extends Argument {

    private final Object value;

    ValuedArgument(final String name, final SQLiteType type, final Object value) {
        super(name, type);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

}
