package sh.miles.prelude.sqlite.arg;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import sh.miles.prelude.sqlite.type.SQLiteType;

/**
 * A SQLiteArgument is a SQLite argument that contains vital information about
 * that argument
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Argument {

    private final String name;
    private final SQLiteType type;

    public ValuedArgument makeValued(@NonNull final Object value) {
        if (this instanceof ValuedArgument) {
            throw new IllegalArgumentException("Cannot make a valued argument from a valued argument");
        }

        return of(this.name, this.type, value);
    }

    public PrimaryArgument makePrimary() {
        if (this instanceof PrimaryArgument) {
            throw new IllegalArgumentException("Cannot make a primary argument from a primary argument");
        }

        return primaryOf(this.name, this.type);
    }

    public static final Argument of(final String name, final SQLiteType type) {
        return new Argument(name, type);
    }

    public static final PrimaryArgument primaryOf(final String name, final SQLiteType type) {
        return new PrimaryArgument(name, type);
    }

    public static final ValuedArgument of(final String name, final SQLiteType type, final Object value) {
        return new ValuedArgument(name, type, value);
    }

    public static final ValuedArgument of(final String name, final Object value) {
        final SQLiteType type = SQLiteType.fromClass(value.getClass());
        if (type == null) {
            throw new IllegalArgumentException("Cannot determine SQLite type for class " + value.getClass().getName());
        }
        return new ValuedArgument(name, type, value);
    }
}
