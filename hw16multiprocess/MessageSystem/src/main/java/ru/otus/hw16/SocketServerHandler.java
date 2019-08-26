package ru.otus.hw16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketServerHandler extends Thread {
    private static Logger logger = LoggerFactory.getLogger(SocketServerHandler.class);

    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public SocketServerHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Сообщение от клиента: " + inputStream.readObject());
            }


        } catch (Exception e) {
            logger.error("error", e);
        }/* finally {
            try {
                inputStream.close();
                outputStream.close();
                clientSocket.close();
            } catch (Exception e) {
                logger.error("error", e);
            }
        }*/
    }
}
