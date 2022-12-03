package sh.miles.prelude.sqlite.arg;

import sh.miles.prelude.sqlite.type.SQLiteType;

/**
 * A PimarySQLiteArgument is a SQLite argument that is a primary key.
 */
public class PrimaryArgument extends Argument {

    PrimaryArgument(final String name, final SQLiteType type) {
        super(name, type);
    }

}
