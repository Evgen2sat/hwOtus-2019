package ru.otus.hw4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ClassObjectCreator {

    public static <T, V> T createClassObject(Class<V> clazz) throws Exception {

        Method[] methods = clazz.getMethods();
        List<Method> markedLogAnnotationMethods = new ArrayList<>();

        for(Method method : methods) {
            if(method.isAnnotationPresent(Log.class)) {
                markedLogAnnotationMethods.add(method);
            }
        }

        InvocationHandler handler = new MyInvocationHandler(clazz.getConstructor().newInstance(), markedLogAnnotationMethods);

        return (T)Proxy.newProxyInstance(ClassObjectCreator.class.getClassLoader(), clazz.getInterfaces(), handler);
    }
}
