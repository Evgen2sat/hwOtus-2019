package ru.otus.hw4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        Test classObject = null;
        try {
            classObject = ClassObjectCreator.createClassObject(TestImpl.class);
            classObject.testMethod1(28);
            classObject.testMethod2(30, "Hello, World");
            classObject.testMethod3("1234");
            classObject.testMethod1("128");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
