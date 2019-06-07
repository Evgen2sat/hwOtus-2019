package ru.otus.hw8;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Person {
    private int param1;
    private int[] param2;
    private static char param3;
    private List<Person> param4;
    private String[] param5;
    private Integer[] param6;

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

    public String[] getParam5() {
        return param5;
    }

    public void setParam5(String[] param5) {
        this.param5 = param5;
    }

    public Integer[] getParam6() {
        return param6;
    }

    public void setParam6(Integer[] param6) {
        this.param6 = param6;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return param1 == person.param1 &&
                Arrays.equals(param2, person.param2) &&
                Objects.equals(param4, person.param4) &&
                Arrays.equals(param5, person.param5) &&
                Arrays.equals(param6, person.param6);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(param1, param4);
        result = 31 * result + Arrays.hashCode(param2);
        result = 31 * result + Arrays.hashCode(param5);
        result = 31 * result + Arrays.hashCode(param6);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "param1=" + param1 +
                ", param2=" + Arrays.toString(param2) +
                ", param4=" + param4 +
                ", param5=" + Arrays.toString(param5) +
                ", param6=" + Arrays.toString(param6) +
                '}';
    }
}
