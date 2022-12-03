package sh.miles.prelude.sqlite.type;

import sh.miles.prelude.sqlite.SQLiteUtils;

/**
 * A SQLiteType is a SQLite type that contains vital information about that type
 */
public enum SQLiteType {

    INTEGER(Integer.class),
    REAL(Double.class),
    TEXT(String.class),
    CHAR(Character.class),
    BLOB(Byte[].class),
    NULL(null);

    private final Class<?> type;

    SQLiteType(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

    public static SQLiteType fromClass(Class<?> type) {
        if (type.isPrimitive()) {
            type = SQLiteUtils.getPRIMITIVE_TO_WRAPPER().get(type);
        }
        for (var value : values()) {
            if (value.getType() == type) {
                return value;
            }
        }
        return null;
    }
}
