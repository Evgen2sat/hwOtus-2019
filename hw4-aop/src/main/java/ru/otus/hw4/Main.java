package ru.otus.hw4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Test classObject = ClassObjectCreator.createClassObject(TestImpl.class);
        classObject.testMethod1(28);
        classObject.testMethod2(30, "Hello, World");
    }
}
