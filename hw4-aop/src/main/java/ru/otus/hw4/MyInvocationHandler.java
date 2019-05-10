package ru.otus.hw4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MyInvocationHandler implements InvocationHandler {

    private final Object interfaceClass;
    private final Set<String> markedLogAnnotationMethodSet;

    MyInvocationHandler(Object interfaceClass) {
        this.interfaceClass = interfaceClass;
        markedLogAnnotationMethodSet = new HashSet<>();

        Method[] methods = interfaceClass.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Log.class)) {
                markedLogAnnotationMethodSet.add(method.getName() + Arrays.toString(method.getParameters()));
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (markedLogAnnotationMethodSet.contains(method.getName() + Arrays.toString(method.getParameters()))) {
            StringBuilder paramsLog = new StringBuilder(method.getName())
                    .append(": ");

            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
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
