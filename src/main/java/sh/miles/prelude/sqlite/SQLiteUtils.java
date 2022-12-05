package sh.miles.prelude.sqlite;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import sh.miles.prelude.sqlite.arg.Argument;
import sh.miles.prelude.sqlite.arg.PrimaryArgument;
import sh.miles.prelude.sqlite.arg.ValuedArgument;

@UtilityClass
public class SQLiteUtils {

    @Getter
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER = new HashMap<>();

    static {
        PRIMITIVE_TO_WRAPPER.put(boolean.class, Boolean.class);
        PRIMITIVE_TO_WRAPPER.put(byte.class, Byte.class);
        PRIMITIVE_TO_WRAPPER.put(char.class, Character.class);
        PRIMITIVE_TO_WRAPPER.put(short.class, Short.class);
        PRIMITIVE_TO_WRAPPER.put(int.class, Integer.class);
        PRIMITIVE_TO_WRAPPER.put(long.class, Long.class);
        PRIMITIVE_TO_WRAPPER.put(float.class, Float.class);
        PRIMITIVE_TO_WRAPPER.put(double.class, Double.class);
        PRIMITIVE_TO_WRAPPER.put(byte[].class, Byte[].class);
    }

    /**
     * Uses string builder to build a selection query
     * 
     * @param selector target selector argument
     * @param columns  target columns
     * @return a string representation of the query
     */
    public static final String buildSelectQuery(final String tableName, final ValuedArgument selector,
            final Argument... columns) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        if (columns.length > 0) {
            for (int i = 0; i < columns.length; i++) {
                builder.append(columns[i].getName());
                if (i < columns.length - 1) {
                    builder.append(", ");
                }
            }
        } else {
            builder.append("*");
        }
        builder.append(" FROM ");
        builder.append(tableName);
        builder.append(" WHERE ");
        builder.append(selector.getName());
        builder.append(" = ?;");
        return builder.toString();
    }

    public static final String buildSelectAllQuery(final String tableName, final Argument... columns) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        if (columns.length > 0) {
            for (int i = 0; i < columns.length; i++) {
                builder.append(columns[i].getName());
                if (i < columns.length - 1) {
                    builder.append(", ");
                }
            }
        } else {
            builder.append("* ");
        }
        builder.append(" FROM ");
        builder.append(tableName);
        builder.append(";");
        return builder.toString();
    }

    public static final String buildExistsQuery(final String tableName, final Argument selector) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM ")
                .append(tableName)
                .append(" WHERE ")
                .append(selector.getName())
                .append(" = ?;");
        return builder.toString();
    }

    /**
     * Uses string builder to build a selection query
     * 
     * @param tableName
     * @param selector
     * @return
     */
    public static final String buildDeleteQuery(final String tableName, final ValuedArgument selector) {
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM ");
        builder.append(tableName);
        builder.append(" WHERE ");
        builder.append(selector.getName());
        builder.append(" = ?;");
        return builder.toString();
    }

    /**
     * Uses string builder to build an update query
     * 
     * @param tableName
     * @param selector
     * @param updates
     * @return
     */
    public static final String buildUpdateQuery(final String tableName, final ValuedArgument selector,
            ValuedArgument... updates) {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ")
                .append(tableName)
                .append(" SET ");
        for (int i = 0; i < updates.length; i++) {
            final ValuedArgument update = updates[i];
            builder.append(update.getName())
                    .append(" = ?");
            if (i < updates.length - 1) {
                builder.append(", ");
            }
        }
        builder.append(" WHERE ")
                .append(selector.getName())
                .append(" = ?;");
        return builder.toString();
    }

    /**
     * Uses string builder to build a insertion query
     * 
     * @param tableName
     * @param valuedArguments
     * @return
     */
    public static final String buildInsertQuery(final String tableName, final ValuedArgument... valuedArguments) {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ");
        builder.append(tableName);
        builder.append(" (");
        for (int i = 0; i < valuedArguments.length; i++) {
            builder.append(valuedArguments[i].getName());
            if (i < valuedArguments.length - 1) {
                builder.append(", ");
            }
        }
        builder.append(") VALUES (");
        for (int i = 0; i < valuedArguments.length; i++) {
            builder.append("?");
            if (i < valuedArguments.length - 1) {
                builder.append(", ");
            }
        }
        builder.append(");");
        return builder.toString();
    }

    /**
     * Uses string builder to build a table creation query
     * 
     * @param tableName
     * @param arguments
     * @return
     */
    public static final String buildCreateExecution(final String tableName, List<Argument> arguments) {
        final StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(tableName);
        builder.append(" (");
        for (var i = 0; i < arguments.size(); i++) {
            final var argument = arguments.get(i);
            builder.append(argument.getName());
            builder.append(" ");
            builder.append(argument.getType());
            if (argument instanceof PrimaryArgument) {
                builder.append(" PRIMARY KEY");
            }
            if (i < arguments.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(");");
        return builder.toString();
    }

    /**
     * Creates a new instance with an empty constructor of the given class
     * 
     * @param <T>   type of the class
     * @param clazz target class
     * @return a new instance of the given class
     */
    @SuppressWarnings("all")
    public static <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.getConstructor(null).newInstance(null);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the value of the field
     * 
     * @param object target object
     * @param field  target field
     * @param value  value to set
     */
    public static void setField(Object object, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
