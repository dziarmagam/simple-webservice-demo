package demo.util;

import demo.exception.FieldIsRequiredException;

import java.util.function.Function;

/**
 * Utils class operating on objects fields
 */
public final class FieldUtils {
    private FieldUtils(){

    }

    /**
     * Return value of getter or null if object is null.
     * @param object Object with value to extract
     * @param getter {@link Function} to get value from object
     * @param <T> Object type
     * @param <S> return value type
     * @return value of getter or null if object is null.
     */
    public static <T,S> S getValueOrNull(T object, Function<T,S> getter){
        return object == null ? null : getter.apply(object);
    }

    /**
     * Check if field is null
     * @param field field to check
     * @throws FieldIsRequiredException when field is null
     */
    public static void checkForNullField(Object field, String fieldName) throws FieldIsRequiredException {
        if(field == null){
            throw new FieldIsRequiredException(fieldName);
        }
    }
}
