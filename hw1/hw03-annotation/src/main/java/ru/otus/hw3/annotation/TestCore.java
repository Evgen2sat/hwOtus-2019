package ru.otus.hw3.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestCore {
    public static void run(Class<?> clazz) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Map<String, List<Method>> testAnnotationMethodsMap = getMethodsWithAnnotationsForTest(clazz.getMethods());
        invoke(testAnnotationMethodsMap, clazz.getConstructor());
    }

    /**
     * Выполнить методы, которые не помечены аннотацией @Test (@BeforeAll, @BeforeEach, @AfterEach, @AfterAll)
     * @param testAnnotationMethodsMap набор методов, помеченные аннотациями @Test, @BeforeAll, @BeforeEach, @AfterEach, @AfterAll
     * @param classObject объект тестируемого класс
     * @param annotationName наименование аннотации
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static void invokeMethod(Map<String, List<Method>> testAnnotationMethodsMap, Object classObject, String annotationName) throws InvocationTargetException, IllegalAccessException {
        List<Method> methods = testAnnotationMethodsMap.get(annotationName);

        for (Method method : methods) {
            method.invoke(classObject);
        }
    }

    /**
     * Выполнить методы, которые помечены аннотацией @Test
     * @param constructor констуктор по умолчанию тестируемого класса
     * @param testAnnotationMethodsMap набор методов, помеченные аннотациями @Test, @BeforeAll, @BeforeEach, @AfterEach, @AfterAll
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    private static void invokeTestMethod(Constructor<?> constructor, Map<String, List<Method>> testAnnotationMethodsMap) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        List<Method> methods = testAnnotationMethodsMap.get("Test");

        for (Method method : methods) {
            Object classObject = constructor.newInstance();
            invokeMethod(testAnnotationMethodsMap, classObject, "BeforeEach");
            method.invoke(classObject);
            invokeMethod(testAnnotationMethodsMap, classObject, "AfterEach");
        }
    }

    /**
     * Выполнить все методы, помеченные аннотациями для теста
     * @param testAnnotationMethodsMap набор методов, помеченные аннотациями @Test, @BeforeAll, @BeforeEach, @AfterEach, @AfterAll
     * @param constructor констуктор по умолчанию тестируемого класса
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static void invoke(Map<String, List<Method>> testAnnotationMethodsMap, Constructor<?> constructor) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        invokeMethod(testAnnotationMethodsMap, null, "BeforeAll");
        invokeTestMethod(constructor, testAnnotationMethodsMap);
        invokeMethod(testAnnotationMethodsMap, null, "AfterAll");
    }

    /**
     * Получить методы, необходимые для теста
     * @param methods все публичные методы тестируемого класса
     * @return набор методов, помеченные аннотациями @Test, @BeforeAll, @BeforeEach, @AfterEach, @AfterAll
     */
    private static Map<String, List<Method>> getMethodsWithAnnotationsForTest(Method[] methods) {
        Map<String, List<Method>> testAnnotationMethodsMap = new HashMap<>();

        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeEach.class)) {
                testAnnotationMethodsMap.computeIfAbsent("BeforeEach", k -> new ArrayList<>()).add(method);
            } else if (method.isAnnotationPresent(AfterEach.class)) {
                testAnnotationMethodsMap.computeIfAbsent("AfterEach", k -> new ArrayList<>()).add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testAnnotationMethodsMap.computeIfAbsent("Test", k -> new ArrayList<>()).add(method);
            } else if (method.isAnnotationPresent(BeforeAll.class)) {
                testAnnotationMethodsMap.computeIfAbsent("BeforeAll", k -> new ArrayList<>()).add(method);
            } else if (method.isAnnotationPresent(AfterAll.class)) {
                testAnnotationMethodsMap.computeIfAbsent("AfterAll", k -> new ArrayList<>()).add(method);
            }
        }

        return testAnnotationMethodsMap;
    }
}
