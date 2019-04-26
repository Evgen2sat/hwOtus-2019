package ru.otus.hw3;

import ru.otus.hw3.annotation.*;

public class TestClass {
    @BeforeEach
    public void beforeEach1() {
        System.out.println("BeforeEach1");
    }

    @BeforeEach
    public void beforeEach2() {
        System.out.println("BeforeEach2");
    }

    @AfterEach
    public void afterEach1() {
        System.out.println("AfterEach1");
    }

    @AfterEach
    public void afterEach2() {
        System.out.println("AfterEach2");
    }

    public TestClass() {
        System.out.println("Constructor");
    }

    @Test
    public void test1() {
        System.out.println("Test1");
    }

    @Test
    public void test2() {
        System.out.println("Test2");
    }

    @BeforeAll
    public static void beforeAll1() {
        System.out.println("BeforeAll1");
    }

    @BeforeAll
    public static void beforeAll2() {
        System.out.println("BeforeAll2");
    }

    @AfterAll
    public static void afterAll1() {
        System.out.println("AfterAll1");
    }

    @AfterAll
    public static void afterAll2() {
        System.out.println("AfterAll2");
    }
}
