package ru.otus.hw9;

import java.sql.Types;

public class QueryParam {
    private int type;
    private Object value;

    public int getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public QueryParam(int type, Object value) {
        this.type = type;
        this.value = value;
    }

    public static QueryParam getLong(long value) {
        return new QueryParam(Types.BIGINT, value);
    }

    public static QueryParam getLongNull() {
        return new QueryParam(Types.BIGINT, null);
    }

    public static QueryParam getInt(int value) {
        return new QueryParam(Types.INTEGER, value);
    }

    public static QueryParam getIntNull() {
        return new QueryParam(Types.INTEGER, null);
    }

    public static QueryParam getString(String value) {
        return new QueryParam(Types.VARCHAR, value);
    }

    public static QueryParam getStringNull() {
        return new QueryParam(Types.VARCHAR, null);
    }
}
