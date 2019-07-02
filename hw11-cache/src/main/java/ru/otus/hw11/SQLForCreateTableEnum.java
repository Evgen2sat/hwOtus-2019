package ru.otus.hw11;

public enum SQLForCreateTableEnum {
    USER("CREATE TABLE user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))"),
    ACCOUNT("CREATE TABLE account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)");

    private final String sql;

    SQLForCreateTableEnum(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
