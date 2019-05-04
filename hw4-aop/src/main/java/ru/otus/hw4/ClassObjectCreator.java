package ru.otus.hw4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class ClassObjectCreator {

    public static <T, V> T createClassObject(Class<V> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        InvocationHandler handler = new MyInvocationHandler(clazz.getConstructor().newInstance());

        return (T)Proxy.newProxyInstance(ClassObjectCreator.class.getClassLoader(), clazz.getInterfaces(), handler);
    }
}
