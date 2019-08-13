package ru.otus.hw15;

import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
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

    public static void main(String[] args) {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        SpringApplication.run(Main.class, args);
    }
}