package sh.miles.prelude.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NonNull;
import sh.miles.prelude.Utils;
import sh.miles.prelude.pojo.IPojo;
import sh.miles.prelude.pojo.PojoValue;

/**
 * A CSV Reader specializes in reading techniques for CSV files.
 */
public class CSVReader extends BufferedReader {

    @Getter
    private final List<String> headers;

    public CSVReader(@NonNull final Reader reader) throws IOException {
        super(reader);
        this.headers = Arrays.asList(readLine().split(","));
    }

    /**
     * Reads a CSV file and returns a list of POJOs.
     * 
     * @return
     * @throws IOException
     */
    public String[] readLineAsArray() throws IOException {
        return readLine().split(",");
    }

    /**
     * Reads a CSV file and returns a list of POJOs.
     * 
     * @param <T>  The type of the POJO.
     * @param type The class of the POJO.
     * @return the POJO Read
     * @throws IOException if an I/O error occurs.
     */
    public <T extends IPojo> T read(@NonNull final Class<T> type) throws IOException {
        final String line = readLine();
        if (line == null) {
            return null;
        }

        final String[] values = line.split(",");
        final T pojo = Utils.newEmptyInstance(type);
        final Map<String, Field> pojoFields = Utils.getFieldsAnnotatedWith(type, PojoValue.class)
                .stream()
                .map(field -> {
                    final PojoValue annotation = field.getAnnotation(PojoValue.class);
                    final String value = annotation.column();
                    return new Object[] { field, value };
                }).collect(Collectors.toMap(pair -> (String) pair[1], pair -> (Field) pair[0]));
        for (int i = 0; i < values.length; i++) {
            final String header = headers.get(i);
            final Field field = pojoFields.get(header);
            final Object value = Utils.convertString(values[i], field.getType());
            Utils.setField(pojo, field.getName(), value);
        }
        return pojo;
    }
}
