package ru.otus.hw8;

import java.lang.reflect.*;
import java.util.Collection;

public class JSONObject {

    private StringBuilder jsonBuilder = new StringBuilder();

    public String toJson(Object object) throws IllegalAccessException {
        jsonBuilder.setLength(0);
        //createJsonText(object);
        fillingItem(object);
        return jsonBuilder.toString();
    }

    private void setAccesiblePrivateField(Field field, boolean staticField, Object object) {
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

    private void removeEndChar(StringBuilder jsonBuilder) {
        jsonBuilder.replace(jsonBuilder.length()-1, jsonBuilder.length(), "");
    }

    private void createJsonText(Object object) throws IllegalAccessException {

//        if(object == null) {
//            jsonBuilder
//                    .append((String)null);
//            return;
//        } else if(object.getClass().getDeclaredClasses().length > 0) {
//            fillingItem(object);
//            return;
//        }


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

    private void fillJsonBuilder(Field field, Object object) throws IllegalAccessException {

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

    private void fillingItem(Object item) throws IllegalAccessException {
        if(item == null) {
            jsonBuilder
                    .append((String)null);
        } else if(item.getClass().isArray()) {
            jsonBuilder
                    .append("[");
            for(int i = 0; i < Array.getLength(item); i++) {
                jsonBuilder
                        .append(Array.get(item, i))
                        .append(",");
            }
            removeEndChar(jsonBuilder);

            jsonBuilder
                    .append("]");
        } else if(item instanceof Collection) {
            Collection collection = (Collection)item;
            jsonBuilder
                    .append("[");
            for(var it : collection) {
                if(it != null) {
                    fillingItem(it);

                    jsonBuilder
                            .append(",");
                }
            }
            removeEndChar(jsonBuilder);

            jsonBuilder.append("]");
        } else if(item instanceof String || item instanceof Character) {
            jsonBuilder
                    .append("\"")
                    .append(item)
                    .append("\"");
        } else if (item instanceof Number) {
            jsonBuilder
                    .append(item);
        } else {
            createJsonText(item);
        }
    }
}
