package ru.otus.hw8;

import java.util.List;

public class Person {
    private int param1;
    private int[] param2;
    private static char param3;
    private List<Person> param4;

    public int getParam1() {
        return param1;
    }

    public void setParam1(int param1) {
        this.param1 = param1;
    }

    public int[] getParam2() {
        return param2;
    }

    public void setParam2(int[] param2) {
        this.param2 = param2;
    }

    public static char getParam3() {
        return param3;
    }

    public static void setParam3(char param3) {
        Person.param3 = param3;
    }

    public List<Person> getParam4() {
        return param4;
    }

    public void setParam4(List<Person> param4) {
        this.param4 = param4;
    }
}
