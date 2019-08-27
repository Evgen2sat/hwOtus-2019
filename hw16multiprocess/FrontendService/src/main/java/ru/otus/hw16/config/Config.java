package ru.otus.hw16.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.otus.hw16.FrontendServiceImpl;
import ru.otus.hw16.TestSocket;
import ru.otus.hw16.messageSystem.Address;

import java.io.IOException;

@Configuration
public class Config {
    private static Logger logger = LoggerFactory.getLogger(Config.class);

//    @Bean
//    public TestSocket initTestSocket() {
//        TestSocket testSocket = new TestSocket();
//        try {
//            testSocket.startConnection("127.0.0.1", 4444);
//        } catch (IOException e) {
//            logger.error("error", e);
//        }
//
//        return testSocket;
//    }

    @Bean
    public FrontendServiceImpl initFrontendServiceImpl(SimpMessagingTemplate simpMessagingTemplate) {
        FrontendServiceImpl frontendService = new FrontendServiceImpl(new Address(), simpMessagingTemplate);
        try {
            frontendService.startConnection("127.0.0.1", 4444);
//            frontendService.stopConnection();
        } catch (IOException e) {
            logger.error("error", e);
        }

        return frontendService;
    }
}
