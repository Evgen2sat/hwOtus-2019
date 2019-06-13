package ru.otus.hw8;

import java.lang.reflect.Field;

public class ReflectionHelper {
    public static void setAccesiblePrivateField(Field field, boolean staticField, Object object) {
        if(!staticField) {
            if(!field.canAccess(object)) {
                field.setAccessible(true);
            }
        } else {
            if(!field.canAccess(null)) {
                field.setAccessible(true);
            }
        }
    }
}
