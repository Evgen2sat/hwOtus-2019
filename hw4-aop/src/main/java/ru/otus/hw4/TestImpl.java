package ru.otus.hw4;

public class TestImpl implements Test {

    @Override
    public void testMethod1(int param) {
        System.out.println(param);
    }

    @Override
    public void testMethod2(int param1, String param2) {
        System.out.println(param1 + " " + param2);
    }
}
