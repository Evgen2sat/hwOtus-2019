package ru.otus.hw6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<BillValue> cellsConfig = new ArrayList<>();
        cellsConfig.add(BillValue.Value_50);
        cellsConfig.add(BillValue.Value_100);
        cellsConfig.add(BillValue.Value_200);
        cellsConfig.add(BillValue.Value_500);
        cellsConfig.add(BillValue.Value_1000);
        cellsConfig.add(BillValue.Value_2000);
        cellsConfig.add(BillValue.Value_5000);

        ATM atm = new ATM(cellsConfig);

        //int insertSum = 150_100;

        Map<Integer, BillValue> insertedCash = new HashMap<>();
        insertedCash.put(5, BillValue.Value_1000);
        insertedCash.put(29, BillValue.Value_5000);
        insertedCash.put(1, BillValue.Value_100);

        int insertSum = ATM.getSumInPackBills(insertedCash);

        System.out.println("Вносится: " + insertSum);
        Map<Integer, BillValue> insertedBills = atm.addCash(insertedCash);
        System.out.println("Внесены купюры:");
        //insertedBills.forEach((key, value) -> System.out.println(value.getValue() + ": " + key));
        System.out.println(insertedBills);
        System.out.println("Внесено: " + ATM.getSumInPackBills(insertedBills));
        System.out.println("Возвращено: " + (insertSum - ATM.getSumInPackBills(insertedBills)));
        System.out.println("Баланс: " + atm.getBalance());
        System.out.println("Баланс: " + atm.getBalanceSum());

        System.out.println("\n");

        int issueSum = 4000;

        System.out.println("Запрашивается: " + issueSum);
        Map<Integer, BillValue> issuedBills = atm.getCash(issueSum);
        if (issuedBills.isEmpty()) {
            System.out.println("В банкомате не хватает средств");
        } else {
            System.out.println("Выдано: " + ATM.getSumInPackBills(issuedBills));
            System.out.println("Выданы купюры:");
            //issuedBills.forEach((key, value) -> System.out.println(value.getValue() + ": " + key));
            System.out.println(issuedBills);
            System.out.println("Не выдано: " + (issueSum - ATM.getSumInPackBills(issuedBills)));
            System.out.println("Баланс: " + atm.getBalance());
            System.out.println("Баланс: " + atm.getBalanceSum());
        }
    }
}
