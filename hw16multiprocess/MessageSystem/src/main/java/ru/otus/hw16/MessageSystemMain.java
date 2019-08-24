package ru.otus.hw16;

public class MessageSystemMain {
    public static void main(String[] args) {
        DBService dbService = new DBServiceImpl();
        FrontendService frontendService = new FrontendServiceImpl();
    }
}
