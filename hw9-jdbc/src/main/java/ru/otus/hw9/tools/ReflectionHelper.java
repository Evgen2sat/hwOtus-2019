package ru.otus.hw9.tools;

import ru.otus.hw9.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class ReflectionHelper {
    private ReflectionHelper() {}

    public static String getClassName(Object object) {
        return object.getClass().getSimpleName().toLowerCase();
    }

    public static Object getValueFromField(String fieldName, Object object) throws Exception {
        return object.getClass().getDeclaredField(fieldName).get(object);
    }

    public static String getColumnsByFields(Object object) {
        if(object == null) {
            return null;
        }

        return Arrays.stream(object.getClass().getFields()).filter(item -> !item.isAnnotationPresent(Id.class)).map(Field::getName).collect(Collectors.joining(","));
    }

    public static String getParams(Object object) {
        if(object == null) {
            return null;
        }

        return Arrays.stream(object.getClass().getFields()).filter(item -> !item.isAnnotationPresent(Id.class)).map(item -> {
            return "?";
        }).collect(Collectors.joining(","));
    }

    public static boolean checkAnnotation(Object object, Class<? extends Annotation> clazz) {
        if(object == null) {
            return false;
        }

        Field[] declaredFields = object.getClass().getDeclaredFields();

        for(Field field : declaredFields) {
            if(field.isAnnotationPresent(clazz)) {
                return true;
            }
        }

        return false;
    }

    public static boolean checkNullIdField(Object object, Class<? extends Annotation> clazz) throws IllegalAccessException {
        if(object == null) {
            return true;
        }

        Field[] declaredFields = object.getClass().getDeclaredFields();
        for(Field field : declaredFields) {
            if(field.isAnnotationPresent(clazz)) {
                return field.get(object) == null;
            }
        }

        return true;
    }
}
