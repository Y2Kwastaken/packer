package sh.miles.prelude.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import sh.miles.prelude.sqlite.arg.Argument;

/**
 * Represents a connection to a SQLite database.
 */
public class SQLiteConnection {

    private final Connection connection;

    public SQLiteConnection(String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
    }

    /**
     * Creates a table with the given name and arguments.
     * 
     * @param name the name of the table
     * @param args the arguments of the table
     * @return the number of rows affected
     */
    public SQLiteTable table(String name, Argument... args) {
        return new SQLiteTable(name, this.connection, args);
    }

    /**
     * Closes the connection to the SQLite database.
     * 
     * @throws SQLException if an error occurs while closing the connection
     */
    public void close() throws SQLException {
        this.connection.close();
    }

}
