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

        if(field.get(object) == null) {
            jsonBuilder
                    .append((String)null)
                    .append(",");
            return;
        }

        if(field.get(object).getClass().isArray()) {
            Object oArray = field.get(object);
            jsonBuilder
                    .append("[");

            for(int i = 0; i < Array.getLength(oArray); i++) {
                jsonBuilder
                        .append(Array.get(oArray, i))
                        .append(",");
            }

            removeEndChar(jsonBuilder);

            jsonBuilder
                    .append("],");
        } else if(field.getGenericType() instanceof ParameterizedType) {
            Collection collection = (Collection)field.get(object);
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
            jsonBuilder
                    .append(field.get(object))
                    .append(",");
        }
    }
}
