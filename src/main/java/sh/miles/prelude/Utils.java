package sh.miles.prelude;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER = new HashMap<>();

    static {
        PRIMITIVE_TO_WRAPPER.put(boolean.class, Boolean.class);
        PRIMITIVE_TO_WRAPPER.put(byte.class, Byte.class);
        PRIMITIVE_TO_WRAPPER.put(char.class, Character.class);
        PRIMITIVE_TO_WRAPPER.put(double.class, Double.class);
        PRIMITIVE_TO_WRAPPER.put(float.class, Float.class);
        PRIMITIVE_TO_WRAPPER.put(int.class, Integer.class);
        PRIMITIVE_TO_WRAPPER.put(long.class, Long.class);
        PRIMITIVE_TO_WRAPPER.put(short.class, Short.class);
    }

    @SuppressWarnings("all")
    public static <T> T newEmptyInstance(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor(null);
            constructor.setAccessible(true);
            T instance = constructor.newInstance(null);
            constructor.setAccessible(false);
            return instance;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setField(@NonNull final Object instance, @NonNull final String fieldName,
            @NonNull final Object value) {
        try {
            final Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
            field.setAccessible(false);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public static List<Field> getFieldsAnnotatedWith(@NonNull final Class<?> type,
            @NonNull final Class<? extends Annotation> annotation) {
        final List<Field> fields = new ArrayList<>();
        for (final Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotation)) {
                fields.add(field);
            }
        }
        return fields;
    }

    public static Class<?> getWrapperType(@NonNull final Class<?> type) {
        return PRIMITIVE_TO_WRAPPER.get(type);
    }

    public static Object convertString(@NonNull final String str, @NonNull Class<?> type) {
        if (type == String.class) {
            return str;
        }
        if (type.isPrimitive()) {
            type = getWrapperType(type);
        }
        try {
            return type.getMethod("valueOf", new Class[] { String.class }).invoke(null, new Object[] { str });
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

}
