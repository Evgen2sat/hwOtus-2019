package ru.otus.hw16.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw16.TestSocket;

import java.io.IOException;

@Configuration
public class Config {
    @Bean
    public TestSocket initTestSocket() {
        TestSocket testSocket = new TestSocket();
        try {
            testSocket.startConnection("127.0.0.1", 4444);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return testSocket;
    }
}
