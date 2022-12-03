package sh.miles.prelude.csv;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

import lombok.NonNull;

/**
 * A CSV Writer specializes in writing techniques for CSV files.
 */
public class CSVWriter extends BufferedWriter {

    public CSVWriter(@NonNull final Writer writer) {
        super(writer);
    }

    /**
     * Writes a CSV file and returns a list of POJOs
     * 
     * @param values The values to write
     * @throws IOException if an I/O error occurs.
     */
    public void write(@NonNull final String[] values) throws IOException {
        for (int i = 0; i < values.length; i++) {
            write(values[i]);
            if (i < values.length - 1) {
                write(",");
            }
        }
        newLine();
    }

    /**
     * Writes a CSV file and returns a list of POJOs
     * 
     * @param values The values to write
     * @throws IOException if an I/O error occurs.
     */
    public void append(@NonNull final String[] values) throws IOException {
        for (int i = 0; i < values.length; i++) {
            append(values[i]);
            if (i < values.length - 1) {
                append(",");
            }
        }
        newLine();
    }

}
