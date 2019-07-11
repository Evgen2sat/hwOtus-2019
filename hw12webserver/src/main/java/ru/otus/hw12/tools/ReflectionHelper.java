package ru.otus.hw12.tools;

import ru.otus.hw12.Id;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ReflectionHelper {
    private ReflectionHelper() {}

    public static String getClassName(Object object) {
        return object.getClass().getSimpleName().toLowerCase();
    }

    public static Object getValueByName(String fieldName, Object object) {
        try {
            Field declaredField = object.getClass().getDeclaredField(fieldName);

            boolean accessibleField = getAccessibleField(declaredField, object);
            setAccesibleField(declaredField, object, true);
            Object result = declaredField.get(object);
            setAccesibleField(declaredField, object, accessibleField);

            return result;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<String> getColumnsByFields(Object object) {
        if(object == null) {
            return null;
        }

        return Arrays.stream(object.getClass().getDeclaredFields()).filter(item -> !item.isAnnotationPresent(Id.class)).map(Field::getName).collect(Collectors.toList());
    }

    public static String getParams(Object object) {
        if(object == null) {
            return null;
        }

        return Arrays.stream(object.getClass().getDeclaredFields()).filter(item -> !item.isAnnotationPresent(Id.class)).map(item -> {
            return "?";
        }).collect(Collectors.joining(","));
    }

    public static boolean checkAnnotation(Object object) {
        if(object == null) {
            return false;
        }

        Field[] declaredFields = object.getClass().getDeclaredFields();

        for(Field field : declaredFields) {
            if(field.isAnnotationPresent(Id.class)) {
                return true;
            }
        }

        return false;
    }

    public static boolean checkNullIdField(Object object) throws IllegalAccessException {
        if(object == null) {
            return true;
        }

        Field[] declaredFields = object.getClass().getDeclaredFields();
        for(Field field : declaredFields) {
            if(field.isAnnotationPresent(Id.class)) {
                return field.get(object) == null;
            }
        }

        return true;
    }

    public static void setAccesibleField(Field field, Object object, boolean accesible) {
        field.setAccessible(accesible);
    }

    public static boolean getAccessibleField(Field field, Object object) {
        return field.canAccess(object);
    }

    public static void setIdToData(long id, Object object) {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for(Field field : declaredFields) {
            if(field.isAnnotationPresent(Id.class)) {
                try {
                    boolean accessibleField = getAccessibleField(field, object);
                    setAccesibleField(field, object, true);
                    field.set(object, id);
                    setAccesibleField(field, object, accessibleField);
                    break;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getIdFieldName(Object object) {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for(Field field : declaredFields) {
            if(field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }

        return null;
    }

    public static List<String> getFieldsName(Class<?> clazz) {
        return Arrays.asList(clazz.getDeclaredFields()).stream().map(field -> field.getName()).collect(Collectors.toList());
    }

    public static String getSimpleClassName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    public static String getIdFieldName(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for(Field field : declaredFields) {
            if(field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }

        return null;
    }

    public static <T> T createObject(Class<T> clazz, ResultSet resultSet, List<String> fieldsName) {
        T result = null;

        try {
            if(resultSet.next()) {
                result = clazz.getDeclaredConstructor().newInstance();
                Field[] declaredFields = result.getClass().getDeclaredFields();
                for(String fieldName : fieldsName) {
                    Field declaredField = result.getClass().getDeclaredField(fieldName);

                    boolean accessibleField = getAccessibleField(declaredField, result);
                    setAccesibleField(declaredField, result, true);
                    declaredField.set(result, resultSet.getObject(fieldName));
                    setAccesibleField(declaredField, result, accessibleField);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
