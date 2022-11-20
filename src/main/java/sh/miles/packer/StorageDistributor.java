package sh.miles.packer;

import java.io.IOException;
import java.sql.SQLException;

import sh.miles.prelude.csv.CSVFile;
import sh.miles.prelude.pojo.IPojo;
import sh.miles.prelude.sqlite.SQLiteConnection;

public class StorageDistributor {

    private static StorageDistributor instance;

    private static final String DB_PATH = "storage.sqlite";

    private final SQLiteConnection connection;

    private StorageDistributor() {
        try {
            connection = new SQLiteConnection(DB_PATH);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SQLiteConnection getSQLiteConnection() {
        return this.connection;
    }

    public <T extends IPojo> CSVFile<T> getCSVFile(final String name, final Class<T> clazz) {
        try {
            return new CSVFile<>(name, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static StorageDistributor getInstance() {
        if (instance == null) {
            instance = new StorageDistributor();
        }
        return instance;
    }

}
