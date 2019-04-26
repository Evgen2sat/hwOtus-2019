package ru.otus.hw3.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestCore {
    private static final String TEST_ANNOTATION = "Test";
    private static final String BEFORE_ALL_ANNOTATION = "BeforeAll";
    private static final String BEFORE_EACH_ANNOTATION = "BeforeEach";
    private static final String AFTER_EACH_ANNOTATION = "AfterEach";
    private static final String AFTER_ALL_ANNOTATION = "AfterAll";

    public static void run(Class<?> clazz) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Map<String, List<Method>> testAnnotationMethodsMap = getMethodsWithAnnotationsForTest(clazz.getMethods());
        invoke(testAnnotationMethodsMap, clazz.getConstructor());
    }

    /**
     * Выполнить методы, которые не помечены аннотацией @Test (@BeforeAll, @BeforeEach, @AfterEach, @AfterAll)
     * @param testAnnotationMethodsMap набор методов, помеченные аннотациями @Test, @BeforeAll, @BeforeEach, @AfterEach, @AfterAll
     * @param classObject объект тестируемого класс
     * @param annotationName наименование аннотации
     */
    private static void invokeMethod(Map<String, List<Method>> testAnnotationMethodsMap, Object classObject, String annotationName) {
        List<Method> methods = testAnnotationMethodsMap.get(annotationName);

        methods.forEach(method -> {
            try {
                method.invoke(classObject);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Выполнить методы, которые помечены аннотацией @Test
     * @param constructor констуктор по умолчанию тестируемого класса
     * @param testAnnotationMethodsMap набор методов, помеченные аннотациями @Test, @BeforeAll, @BeforeEach, @AfterEach, @AfterAll
     */
    private static void invokeTestMethods(Constructor<?> constructor, Map<String, List<Method>> testAnnotationMethodsMap) {
        List<Method> methods = testAnnotationMethodsMap.get(TEST_ANNOTATION);

        methods.forEach(method -> {
            Object classObject = null;
            try {
                classObject = constructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            invokeMethod(testAnnotationMethodsMap, classObject, BEFORE_EACH_ANNOTATION);

            try {
                method.invoke(classObject);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            invokeMethod(testAnnotationMethodsMap, classObject, AFTER_EACH_ANNOTATION);
        });
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
        invokeMethod(testAnnotationMethodsMap, null, BEFORE_ALL_ANNOTATION);
        invokeTestMethods(constructor, testAnnotationMethodsMap);
        invokeMethod(testAnnotationMethodsMap, null, AFTER_ALL_ANNOTATION);
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
                testAnnotationMethodsMap.computeIfAbsent(BEFORE_EACH_ANNOTATION, k -> new ArrayList<>()).add(method);
            } else if (method.isAnnotationPresent(AfterEach.class)) {
                testAnnotationMethodsMap.computeIfAbsent(AFTER_EACH_ANNOTATION, k -> new ArrayList<>()).add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testAnnotationMethodsMap.computeIfAbsent(TEST_ANNOTATION, k -> new ArrayList<>()).add(method);
            } else if (method.isAnnotationPresent(BeforeAll.class)) {
                testAnnotationMethodsMap.computeIfAbsent(BEFORE_ALL_ANNOTATION, k -> new ArrayList<>()).add(method);
            } else if (method.isAnnotationPresent(AfterAll.class)) {
                testAnnotationMethodsMap.computeIfAbsent(AFTER_ALL_ANNOTATION, k -> new ArrayList<>()).add(method);
            }
        }

        return testAnnotationMethodsMap;
    }
}
