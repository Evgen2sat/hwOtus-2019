package ru.otus.hw8;

import java.lang.reflect.*;
import java.util.Collection;

public abstract class JSONObject {

    private static StringBuilder jsonBuilder = new StringBuilder();

    public static String toJson(Object object) throws IllegalAccessException {
        createJsonText(object);
        return jsonBuilder.toString();
    }

    private static void setAccesiblePrivateField(Field field, boolean staticField, Object object) {
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

    private static void removeEndChar(StringBuilder jsonBuilder) {
        jsonBuilder.replace(jsonBuilder.length()-1, jsonBuilder.length(), "");
    }

    private static void createJsonText(Object object) throws IllegalAccessException {
        jsonBuilder
                .append("{");

        Field[] fields = object.getClass().getDeclaredFields();

        for(Field field : fields) {

            boolean staticField = Modifier.isStatic(field.getModifiers());

            setAccesiblePrivateField(field, staticField, object);

            jsonBuilder
                    .append("\"")
                    .append(field.getName())
                    .append("\"")
                    .append(": ");

            if(!staticField) {
                fillJsonBuilder(field, object);
            } else {
                fillJsonBuilder(field, null);
            }
        }

        removeEndChar(jsonBuilder);

        jsonBuilder
                .append("}");
    }

    private static void fillJsonBuilder(Field field, Object object) throws IllegalAccessException {

        Object value = field.get(object);

        if(value == null) {
            jsonBuilder
                    .append((String)null)
                    .append(",");
            return;
        }

        if(value.getClass().isArray()) {
            jsonBuilder
                    .append("[");

            for(int i = 0; i < Array.getLength(value); i++) {

                Object item = Array.get(value, i);

                fillingItem(item);

                jsonBuilder
                        .append(",");
            }

            removeEndChar(jsonBuilder);

            jsonBuilder
                    .append("],");
        } else if(field.getGenericType() instanceof ParameterizedType) {
            Collection collection = (Collection)value;
            jsonBuilder
                    .append("[");
            for(var item : collection) {
                if(item != null) {
                    createJsonText(item);

                    jsonBuilder
                            .append(",");
                }
            }
            removeEndChar(jsonBuilder);

            jsonBuilder.append("],");
        } else {

            fillingItem(value);

            jsonBuilder
                    .append(",");
        }
    }

    private static void fillingItem(Object item) {
        if(item != null && !(item instanceof Number)) {
            jsonBuilder
                    .append("\"")
                    .append(item)
                    .append("\"");
        } else if(item == null) {
            jsonBuilder
                    .append((String)null);
        } else {
            jsonBuilder
                    .append(item);
        }
    }
}
