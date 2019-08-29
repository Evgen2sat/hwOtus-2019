package ru.otus.hw16;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FrontendServiceMain {
    public static int ADDRESS;

    public static void main(String[] args) {
        for(String arg : args) {
            if(arg.startsWith("-address")) {
                ADDRESS = Integer.valueOf(arg.split("=")[1]);
                break;
            }
        }

        SpringApplication.run(FrontendServiceMain.class, args);
    }
}
