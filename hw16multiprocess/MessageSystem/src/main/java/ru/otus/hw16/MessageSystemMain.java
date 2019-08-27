package ru.otus.hw16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MessageSystemMain {
    private static Logger logger = LoggerFactory.getLogger(MessageSystemMain.class);


    private static final String FRONTEND_SERVER_START_COMMAND = "java -jar -Dserver.port=8083 ../../FrontendService/target/frontend-service.jar";
    private static final String DBSERVICE_START_COMMAND = "java -jar ../../DBService/target/dbservice-jar-with-dependencies.jar";

    private ServerSocket serverSocket;

    public static void main(String[] args) throws Exception {
        MessageSystemMain messageSystemMain = new MessageSystemMain();
        messageSystemMain.start(4444);
        messageSystemMain.stop();
    }

    private void start(int port) throws Exception {
        serverSocket = new ServerSocket(port);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(() -> startClient(FRONTEND_SERVER_START_COMMAND));
        executorService.execute(() -> startClient(DBSERVICE_START_COMMAND));

        executorService.shutdown();

        while (true) {
            new SocketServerHandler(serverSocket.accept()).start();
        }
    }

    private void startClient(String command) {
        try {
            new ProcessRunnerImpl().start(command);
        } catch (IOException e) {
            logger.error("error", e);
        }
    }

    private void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.error("error", e);
        }
    }
}
