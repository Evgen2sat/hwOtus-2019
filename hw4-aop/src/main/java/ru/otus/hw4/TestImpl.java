package ru.otus.hw4;

public class TestImpl implements Test {

    @Override
    @Log
    public void testMethod1(int param) {
        System.out.println(param);
    }

    @Override
    @Log
    public void testMethod2(int param1, String param2) {
        System.out.println(param1 + " " + param2);
    }

    @Override
    public void testMethod3(String param) {
        System.out.println(param);
    }

    @Override
    @Log
    public void testMethod1(String param) {
        System.out.println(param);
    }
}
