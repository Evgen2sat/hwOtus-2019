package ru.otus.hw16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.messageSystem.MessageSystem;
import ru.otus.hw16.messageSystem.MessageSystemImpl;
import ru.otus.hw16.messageSystem.processRunner.ProcessRunnerImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageSystemMain {
    private static Logger logger = LoggerFactory.getLogger(MessageSystemMain.class);

    private final int THREAD_COUNT = 4;

    private static final String FRONTEND_SERVER_START_COMMAND = "java -jar -Dserver.port=8083 ../../FrontendService/target/frontend-service.jar -address=100";
    private static final String DBSERVICE_START_COMMAND = "java -jar ../../DBService/target/dbservice-jar-with-dependencies.jar -address=100";
    private static final String FRONTEND_SERVER_START_COMMAND_2 = "java -jar -Dserver.port=8084 ../../FrontendService/target/frontend-service.jar -address=200";
    private static final String DBSERVICE_START_COMMAND_2 = "java -jar ../../DBService/target/dbservice-jar-with-dependencies.jar -address=200";
    private final MessageSystem messageSystem = new MessageSystemImpl();

    private ServerSocket serverSocket;

    public static void main(String[] args) throws Exception {

        MessageSystemMain messageSystemMain = new MessageSystemMain();
        messageSystemMain.start(4444);
        messageSystemMain.stop();
    }

    private void start(int port) throws Exception {
        serverSocket = new ServerSocket(port);

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        executorService.execute(() -> startClient(FRONTEND_SERVER_START_COMMAND));
        executorService.execute(() -> startClient(DBSERVICE_START_COMMAND));
        executorService.execute(() -> startClient(FRONTEND_SERVER_START_COMMAND_2));
        executorService.execute(() -> startClient(DBSERVICE_START_COMMAND_2));

        executorService.shutdown();

        while (true) {
            new SocketServerHandler(serverSocket.accept(), messageSystem).start();
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
