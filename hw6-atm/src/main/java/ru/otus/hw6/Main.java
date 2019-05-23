package ru.otus.hw6;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();

        int insertSum = 150_100;

        System.out.println("Вносится: " + insertSum);
        int insertedSum = atm.addCash(insertSum);
        System.out.println("Внесено: " + insertedSum);
        System.out.println("Возвращено: " + (insertSum - insertedSum));
        System.out.println("Баланс: " + atm.getBalance());

        System.out.println("\n");

        int issueSum = 10_000;

        System.out.println("Запрашивается: " + issueSum);
        int issuedSum = atm.getCash(issueSum);
        System.out.println("Выдано: " + issuedSum);
        System.out.println("Не выдано: " + (issueSum - issuedSum));
        System.out.println("Баланс: " + atm.getBalance());
    }
}