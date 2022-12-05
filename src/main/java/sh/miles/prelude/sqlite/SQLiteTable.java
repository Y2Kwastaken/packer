package sh.miles.prelude.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import sh.miles.prelude.pojo.IPojo;
import sh.miles.prelude.sqlite.arg.Argument;
import sh.miles.prelude.sqlite.arg.ValuedArgument;
import sh.miles.prelude.sqlite.pojo.PojoUtils;

/**
 * Represents a table in a SQLite database
 */
public class SQLiteTable {

    @Getter
    private final String name;
    private final Connection connection;
    private final List<Argument> arguments;

    SQLiteTable(final String name, final Connection connection, final Argument... args) {
        this.name = name;
        this.connection = connection;
        this.arguments = Arrays.asList(args);
    }

    /**
     * Creates the table in the SQLite database
     */
    public final void create() {
        try (var statement = this.connection.createStatement()) {
            statement.execute(SQLiteUtils.buildCreateExecution(this.name, arguments));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Drops the table from the SQLite database
     */
    public void drop() {
        try (var statement = this.connection.createStatement()) {
            final String query = "DROP TABLE IF EXISTS " + this.name + ";";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a row into the table
     * 
     * @param valuedArguments the arguments to insert
     */
    public void insert(final ValuedArgument... valuedArguments) {
        try (PreparedStatement ps = this.connection
                .prepareStatement(SQLiteUtils.buildInsertQuery(this.name, valuedArguments))) {
            for (int i = 0; i < valuedArguments.length; i++) {
                ps.setObject(i + 1, valuedArguments[i].getValue());
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a row in the table
     * 
     * @param selector the selector to use
     * @param updates  the updates to make
     */
    public void update(final ValuedArgument selector, ValuedArgument... updates) {
        try (PreparedStatement ps = this.connection
                .prepareStatement(SQLiteUtils.buildUpdateQuery(this.name, selector, updates))) {
            for (int i = 0; i < updates.length; i++) {
                ps.setObject(i + 1, updates[i].getValue());
            }
            ps.setObject(updates.length + 1, selector.getValue());
            System.out.println(ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a row from the table
     * 
     * @param selector the selector to use
     */
    public void delete(final ValuedArgument selector) {
        try (PreparedStatement ps = this.connection
                .prepareStatement(SQLiteUtils.buildDeleteQuery(this.name, selector))) {
            ps.setObject(1, selector.getValue());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(final ValuedArgument selector) {
        try (PreparedStatement ps = this.connection.prepareStatement(SQLiteUtils.buildExistsQuery(name, selector))) {
            ps.setObject(1, selector.getValue());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Selects a row from the table
     * 
     * @param <T>      the type of the pojo
     * @param selector the selector to use
     * @param type     the type of the pojo
     * @param columns  the columns to select
     * @return the pojo
     */
    public <T extends IPojo> List<T> select(final ValuedArgument selector, Class<T> type,
            Argument... columns) {
        try (PreparedStatement ps = this.connection
                .prepareStatement(SQLiteUtils.buildSelectQuery(this.name, selector, columns))) {
            ps.setObject(1, selector.getValue());
            if (columns.length == 0) {
                columns = this.arguments.toArray(new Argument[this.arguments.size()]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return PojoUtils.map(type, rs, Arrays.asList(columns));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public <T extends IPojo> List<T> selectAll(Class<T> type, Argument... columns) {
        try (PreparedStatement ps = this.connection
                .prepareStatement(SQLiteUtils.buildSelectAllQuery(this.name, columns))) {
            if (columns.length == 0) {
                columns = this.arguments.toArray(new Argument[this.arguments.size()]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return PojoUtils.map(type, rs, Arrays.asList(columns));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
