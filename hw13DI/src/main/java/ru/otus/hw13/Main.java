package ru.otus.hw13;

import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main {

    private static final StandardServiceRegistry standardServiceRegistry;

    static {
        try {
            standardServiceRegistry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static StandardServiceRegistry getStandardServiceRegistry() {
        return standardServiceRegistry;
    }

    public static void main(final String[] args) throws Exception {

    }
}