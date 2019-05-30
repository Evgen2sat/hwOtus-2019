package ru.otus.hw6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.otus.hw6.DepartmentATM.getSumInPackBills;

public class Main {
    public static void main(String[] args) {
        List<BillValue> atmConfig1 = new ArrayList<>();
        atmConfig1.add(BillValue.Value_50);
        atmConfig1.add(BillValue.Value_100);

        List<BillValue> atmConfig2 = new ArrayList<>();
        atmConfig2.add(BillValue.Value_200);
        atmConfig2.add(BillValue.Value_500);

        List<BillValue> atmConfig3 = new ArrayList<>();
        atmConfig3.add(BillValue.Value_1000);
        atmConfig3.add(BillValue.Value_2000);
        atmConfig3.add(BillValue.Value_5000);

        List<BillValue> atmConfig4 = new ArrayList<>();
        atmConfig4.add(BillValue.Value_50);
        atmConfig4.add(BillValue.Value_100);
        atmConfig4.add(BillValue.Value_200);
        atmConfig4.add(BillValue.Value_500);
        atmConfig4.add(BillValue.Value_1000);
        atmConfig4.add(BillValue.Value_2000);
        atmConfig4.add(BillValue.Value_5000);

        Map<Integer, BillValue> insertedCash1 = new HashMap<>();
        insertedCash1.put(10, BillValue.Value_50);
        insertedCash1.put(20, BillValue.Value_100);

        Map<Integer, BillValue> insertedCash2 = new HashMap<>();
        insertedCash2.put(10, BillValue.Value_200);
        insertedCash2.put(20, BillValue.Value_500);

        Map<Integer, BillValue> insertedCash3 = new HashMap<>();
        insertedCash3.put(10, BillValue.Value_1000);
        insertedCash3.put(20, BillValue.Value_2000);
        insertedCash3.put(30, BillValue.Value_5000);

        Map<Integer, BillValue> insertedCash4 = new HashMap<>();
        insertedCash4.put(1, BillValue.Value_50);
        insertedCash4.put(2, BillValue.Value_100);
        insertedCash4.put(3, BillValue.Value_200);
        insertedCash4.put(4, BillValue.Value_500);
        insertedCash4.put(5, BillValue.Value_1000);
        insertedCash4.put(6, BillValue.Value_2000);
        insertedCash4.put(7, BillValue.Value_5000);

        DepartmentATM departmentATM = new DepartmentATM();
        ATMImpl atm1 = departmentATM.createAtm(atmConfig1);
        atm1.addCash(insertedCash1);
        ATMImpl atm2 = departmentATM.createAtm(atmConfig2);
        atm2.addCash(insertedCash2);
        ATMImpl atm3 = departmentATM.createAtm(atmConfig3);
        atm3.addCash(insertedCash3);
        ATMImpl atm4 = departmentATM.createAtm(atmConfig4);
        atm4.addCash(insertedCash4);

        departmentATM.getBalanceFromAllATM();

//        Map<Integer, BillValue> insertedCash = new HashMap<>();
//        insertedCash.put(5, BillValue.Value_1000);
//        insertedCash.put(29, BillValue.Value_5000);
//        insertedCash.put(1, BillValue.Value_100);
//
//        int insertSum = getSumInPackBills(insertedCash);
//
//        System.out.println("Вносится: " + insertSum);
//        Map<Integer, BillValue> insertedBills = atm.addCash(insertedCash);
//        System.out.println("Внесены купюры:");
//        //insertedBills.forEach((key, value) -> System.out.println(value.getValue() + ": " + key));
//        System.out.println(insertedBills);
//        System.out.println("Внесено: " + getSumInPackBills(insertedBills));
//        System.out.println("Возвращено: " + (insertSum - getSumInPackBills(insertedBills)));
//        System.out.println("Баланс: " + atm.getBalance());
//        System.out.println("Баланс: " + atm.getBalanceSum());
//
//        System.out.println("\n");
//
//        int issueSum = 4000;
//
//        System.out.println("Запрашивается: " + issueSum);
//        Map<Integer, BillValue> issuedBills = atm.getCash(issueSum);
//        if (issuedBills.isEmpty()) {
//            System.out.println("В банкомате не хватает средств");
//        } else {
//            System.out.println("Выдано: " + getSumInPackBills(issuedBills));
//            System.out.println("Выданы купюры:");
//            //issuedBills.forEach((key, value) -> System.out.println(value.getValue() + ": " + key));
//            System.out.println(issuedBills);
//            System.out.println("Не выдано: " + (issueSum - getSumInPackBills(issuedBills)));
//            System.out.println("Баланс: " + atm.getBalance());
//            System.out.println("Баланс: " + atm.getBalanceSum());
//        }
    }
}
