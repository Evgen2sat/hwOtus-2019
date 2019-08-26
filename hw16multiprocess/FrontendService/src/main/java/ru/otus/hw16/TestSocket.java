package ru.otus.hw16;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TestSocket {
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public void startConnection(String host, int port) throws IOException {
        System.out.println("Start client");
        clientSocket = new Socket(host, port);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    private void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public void sendMessage(String message) {
        try {
            out.writeObject(message);
//            stopConnection();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

//    private Message sendMessage(Message message) throws IOException, ClassNotFoundException {
//        out.writeObject(message);
//
//        return (Message)in.readObject();
//    }
}
