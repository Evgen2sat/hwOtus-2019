package ru.otus.hw4;

public interface Test {
    @Log
    void testMethod1(int param);

    @Log
    void testMethod2(int param1, String param2);
}
