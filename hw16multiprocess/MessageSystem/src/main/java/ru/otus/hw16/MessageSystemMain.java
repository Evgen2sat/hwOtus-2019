package ru.otus.hw16;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageSystemMain {
    private static final String CLIENT_START_COMMAND = "java -jar ../../FrontendService/target/frontend-service.jar";

    public static void main(String[] args) throws Exception {
        new MessageSystemMain().start();
    }

    private void start() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        startClient(executorService);

        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(CLIENT_START_COMMAND);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }, 5, TimeUnit.SECONDS);

    }
}
