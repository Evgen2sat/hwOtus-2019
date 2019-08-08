package ru.otus.hw15;

import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    }
}