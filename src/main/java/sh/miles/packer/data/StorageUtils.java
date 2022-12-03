package sh.miles.packer.data;

import java.util.List;

import sh.miles.packer.pack.Pack;
import sh.miles.prelude.csv.CSVFile;
import sh.miles.prelude.sqlite.SQLiteTable;
import sh.miles.prelude.sqlite.arg.Argument;
import sh.miles.prelude.sqlite.type.SQLiteType;

public class StorageUtils {

    public static final Argument PACK_NAME = Argument.of("name", SQLiteType.TEXT);
    public static final Argument PACK_ARGUMENT = Argument.of("pack", SQLiteType.TEXT);

    private StorageUtils() {
    }

    public static final void insertBase64PackSQLite(SQLiteTable table, String name, String pack) {
        table.insert(PACK_NAME.makeValued(name), PACK_ARGUMENT.makeValued(pack));
    }

    public static final void insertBase64PackCSV(CSVFile<PackPojo> file, String name, String pack) {
        file.addEntry(new PackPojo(name, pack));
    }

    public static final List<PackPojo> getAllBase64PackSQLite(SQLiteTable table) {
        return table.selectAll(PackPojo.class, PACK_ARGUMENT);
    }

    public static final List<PackPojo> getBase64PackSQLite(SQLiteTable table, String name) {
        return table.select(PACK_NAME.makeValued(name), PackPojo.class, PACK_ARGUMENT);
    }

    public static final List<PackPojo> getBase64PackCSV(CSVFile<PackPojo> file, String name) {
        return file.getEntries().stream().filter(p -> p.getName().equals(name)).toList();
    }

    public static Pack getPackFromPojo(PackPojo pojo) {
        return Pack.fromBase64(pojo.getBase64());
    }

    public static List<Pack> getPacksFromPojos(List<PackPojo> pojos) {
        return pojos.stream().map(StorageUtils::getPackFromPojo).toList();
    }

}
