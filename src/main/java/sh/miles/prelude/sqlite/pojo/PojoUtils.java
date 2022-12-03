package sh.miles.prelude.sqlite.pojo;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.experimental.UtilityClass;
import sh.miles.prelude.pojo.IPojo;
import sh.miles.prelude.pojo.PojoValue;
import sh.miles.prelude.sqlite.SQLiteUtils;
import sh.miles.prelude.sqlite.arg.Argument;

@UtilityClass
public class PojoUtils {

    public static <T extends IPojo> List<T> map(Class<T> clazz, ResultSet resultSet,
            List<Argument> arguments) throws SQLException {
        final List<T> list = new ArrayList<>();

        final Map<String, Field> annotatedFields = getPojoFields(clazz);

        while (resultSet.next()) {
            T instance = SQLiteUtils.createInstance(clazz);
            for (int i = 0; i < arguments.size(); i++) {
                final Argument arg = arguments.get(i);
                final Field field = annotatedFields.get(arg.getName());
                try {
                    field.setAccessible(true);
                    field.set(instance, resultSet.getObject(i + 1));
                    field.setAccessible(false);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            list.add(instance);
        }
        return list;
    }

    private static Map<String, Field> getPojoFields(Class<? extends IPojo> clazz) {
        final Map<String, Field> fields = new HashMap<>();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(PojoValue.class)) {
                fields.put(field.getAnnotation(PojoValue.class).column(), field);
            }
        }

        return fields;
    }
}
