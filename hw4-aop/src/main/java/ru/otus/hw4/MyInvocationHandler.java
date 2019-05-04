package ru.otus.hw4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MyInvocationHandler<T> implements InvocationHandler {

    private final T interfaceClass;

    MyInvocationHandler(Object interfaceClass) {
        this.interfaceClass = (T)interfaceClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.isAnnotationPresent(Log.class)) {
            StringBuilder paramsLog = new StringBuilder(method.getName())
                    .append(": ");

            Parameter[] parameters = method.getParameters();
            for(int i = 0; i < parameters.length; i++) {
                paramsLog
                        .append(parameters[i])
                        .append(" ")
                        .append(args[i])
                        .append("; ");
            }

            System.out.println(paramsLog);
        }
        return method.invoke(interfaceClass, args);
    }
}
