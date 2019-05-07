package ru.otus.hw4;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.stream.Collectors;

public class MyInvocationHandler implements InvocationHandler {

    private final Object interfaceClass;
    private final List<Method> markedLogAnnotationMethods;

    MyInvocationHandler(Object interfaceClass, List<Method> markedLogAnnotationMethods) {
        this.interfaceClass = interfaceClass;
        this.markedLogAnnotationMethods = markedLogAnnotationMethods;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //методы совпадающие по названию и количеству параметров
        List<Method> similarMethodList = markedLogAnnotationMethods.stream()
                .filter(item -> item.getName().equals(method.getName()))
                .filter(item -> item.getParameterCount() == method.getParameterCount())
                .collect(Collectors.toList());

        if(existCurrentMethodInSimilarMethodList(similarMethodList, method)) {
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

    private boolean existCurrentMethodInSimilarMethodList(List<Method> similarMethodList, Method method2) {
        boolean result = false;

        if(!similarMethodList.isEmpty()) {
            for (Method method : similarMethodList) {
                result = true;
                Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    if (!parameters[i].getType().equals(method2.getParameters()[i].getType())) {
                        result = false;
                        break;
                    }
                }

                if(result) {
                    break;
                }
            }
        }

        return result;
    }
}
