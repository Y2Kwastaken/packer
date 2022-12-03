package sh.miles.packer.data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import sh.miles.packer.pack.Pack;
import sh.miles.prelude.csv.CSVFile;
import sh.miles.prelude.sqlite.SQLiteConnection;
import sh.miles.prelude.sqlite.SQLiteTable;

public class PackerStorage {

    public static final String DATABASE_NAME = "packer.sqlite";
    public static final String CSV_NAME = "%s.csv";

    boolean useSQLite;
    private CSVFile<PackPojo> csvFile;
    private SQLiteConnection connection;
    private SQLiteTable table;

    public PackerStorage() {
        // reserved for static class
    }

    public void create(String name) {
        if (this.useSQLite) {
            table = connection.table(name, StorageUtils.PACK_NAME.makePrimary(), StorageUtils.PACK_ARGUMENT);
            table.create();
            return;
        }
        try {
            csvFile = new CSVFile<>(String.format(CSV_NAME, name), PackPojo.class, List.of("key", "pack"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertPack(Pack pack) {
        final String name = pack.getName();
        final String base64 = pack.toBase64();
        if (this.useSQLite) {
            StorageUtils.insertBase64PackSQLite(table, name, base64);
        } else {
            StorageUtils.insertBase64PackCSV(csvFile, name, base64);
        }
    }

    public List<Pack> getPacksWithName(String name) {
        final List<PackPojo> pojos;
        if (this.useSQLite) {
            pojos = StorageUtils.getBase64PackSQLite(table, name);
        } else {
            pojos = StorageUtils.getBase64PackCSV(csvFile, name);
        }
        return StorageUtils.getPacksFromPojos(pojos);
    }

    public List<Pack> getAllPacks() {
        final List<PackPojo> pojos;
        if (this.useSQLite) {
            pojos = StorageUtils.getAllBase64PackSQLite(table);
        } else {
            pojos = csvFile.getEntries();
        }
        return StorageUtils.getPacksFromPojos(pojos);
    }

    public void removePack(String name) {
        if (this.useSQLite) {
            table.delete(StorageUtils.PACK_NAME.makeValued(name));
        } else {
            final List<PackPojo> packs = StorageUtils.getBase64PackCSV(csvFile, name);
            for (PackPojo pack : packs) {
                csvFile.removeEntry(pack);
            }
        }
    }

    public void useDatabase() {
        try {
            this.useSQLite = true;
            this.connection = new SQLiteConnection(DATABASE_NAME);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create SQLite connection Fatal Error", e);
        }
    }

    public void close() {
        if (this.useSQLite) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Failed to close SQLite connection Fatal Error", e);
            }
        } else {
            try {
                this.csvFile.save();
            } catch (IOException e) {
                throw new RuntimeException("Failed to close CSV file Fatal Error", e);
            }
        }
    }

}
