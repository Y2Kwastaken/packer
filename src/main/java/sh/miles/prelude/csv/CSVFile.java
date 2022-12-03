package sh.miles.prelude.csv;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;
import sh.miles.prelude.Utils;
import sh.miles.prelude.pojo.IPojo;
import sh.miles.prelude.pojo.PojoValue;

/**
 * Provides a CSV file representation. Each CSV row is represented as a pojo
 * which can be created with {@link IPojo} interface and {@link PojoValue}
 * annotation.
 */
public class CSVFile<T extends IPojo> {

    private final File file;
    @Getter
    private final List<String> headers;
    @Getter
    private final List<T> entries;

    /**
     * Creates a new CSV file representation.
     * 
     * @param path the path to the CSV file
     * @param type the type of the pojo which represents each CSV row
     * @throws IOException if an I/O error occurs
     */
    public CSVFile(final String path, final Class<T> type) throws IOException {
        this.file = new File(path);
        this.headers = new ArrayList<>();
        this.entries = new ArrayList<>();
        init(type);
    }

    public CSVFile(final String path, final Class<T> type, final List<String> headers) throws IOException {
        this.file = new File(path);
        this.headers = headers;
        this.entries = new ArrayList<>();
        init(type);
    }

    /**
     * Initializes the CSV file. If the file does not exist, it is created if it has
     * data, it is read.
     * 
     * @param type the type of pojo to be represented by the CSV file
     * @throws IOException if an I/O error occurs
     */
    protected void init(Class<T> type) throws IOException {
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new IOException("Could not create file: " + file.getAbsolutePath());
            }
            return;
        }

        try (final CSVReader reader = new CSVReader(new FileReader(this.file))) {
            if (this.headers.isEmpty()) {
                this.headers.addAll(reader.getHeaders());
            }
            T entry;
            while ((entry = reader.read(type)) != null) {
                this.entries.add(entry);
            }
        }
    }

    /**
     * Adds a new entry to the CSV file.
     * 
     * @param entry the entry to be added
     */
    public void addEntry(final T entry) {
        this.entries.add(entry);
    }

    /**
     * Writes the CSV file.
     * 
     * @param entry
     */
    public void removeEntry(final T entry) {
        this.entries.remove(entry);
    }

    /**
     * Saves the CSV file with specific column format specified by the function
     * given.
     * 
     * @param saveFormat the function which specifies column format
     * @throws IOException if an I/O write error occurs
     */
    public void save(Function<T, String[]> saveFormat) throws IOException {
        try (final CSVWriter writer = new CSVWriter(new FileWriter(this.file))) {
            writer.write(this.headers.toArray(String[]::new));
            for (T entry : this.entries) {
                writer.write(saveFormat.apply(entry));
            }
        }
    }

    /**
     * Saves the CSV file with default best-effort prediction of column format
     * 
     * @throws IOException if an I/O write error occurs
     */
    public void save() throws IOException {
        save((T entry) -> {

            String[] values = new String[this.headers.size()];
            final Map<String, Field> pojoFields = Utils.getFieldsAnnotatedWith(entry.getClass(), PojoValue.class)
                    .stream()
                    .map(field -> {
                        final PojoValue annotation = field.getAnnotation(PojoValue.class);
                        final String value = annotation.column();
                        return new Object[] { field, value };
                    }).collect(Collectors.toMap(pair -> (String) pair[1], pair -> (Field) pair[0]));
            for (int i = 0; i < this.headers.size(); i++) {
                final String header = this.headers.get(i);
                final Field field = pojoFields.get(header);
                if (field != null) {
                    try {
                        field.setAccessible(true);
                        values[i] = field.get(entry).toString();
                        field.setAccessible(false);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            return values;
        });
    }
}
