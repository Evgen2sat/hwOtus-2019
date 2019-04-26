package ru.otus.hw3;

import ru.otus.hw3.annotation.TestCore;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestCore.run(TestClass.class);
    }
}
