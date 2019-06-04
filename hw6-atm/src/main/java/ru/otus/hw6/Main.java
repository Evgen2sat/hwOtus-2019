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

        Map<BillValue, Integer> insertedCash1 = new HashMap<>();
        insertedCash1.put(BillValue.Value_50, 10);
        insertedCash1.put(BillValue.Value_100, 20);

        Map<BillValue, Integer> insertedCash2 = new HashMap<>();
        insertedCash2.put(BillValue.Value_200, 10);
        insertedCash2.put(BillValue.Value_500, 20);

        Map<BillValue, Integer> insertedCash3 = new HashMap<>();
        insertedCash3.put(BillValue.Value_1000, 10);
        insertedCash3.put(BillValue.Value_2000, 20);
        insertedCash3.put(BillValue.Value_5000, 30);

        Map<BillValue, Integer> insertedCash4 = new HashMap<>();
        insertedCash4.put(BillValue.Value_50, 1);
        insertedCash4.put(BillValue.Value_100, 2);
        insertedCash4.put(BillValue.Value_200, 3);
        insertedCash4.put(BillValue.Value_500, 4);
        insertedCash4.put(BillValue.Value_1000, 5);
        insertedCash4.put(BillValue.Value_2000, 6);
        insertedCash4.put(BillValue.Value_5000, 7);

        DepartmentATM departmentATM = new DepartmentATM();
        ATMImpl atm1 = departmentATM.createAtm(atmConfig1, insertedCash1);
        ATMImpl atm2 = departmentATM.createAtm(atmConfig2, insertedCash2);
        ATMImpl atm3 = departmentATM.createAtm(atmConfig3, insertedCash3);
        ATMImpl atm4 = departmentATM.createAtm(atmConfig4, insertedCash4);

        departmentATM.getBalanceFromAllATM();

        atm1.addCash(insertedCash1);
        atm2.addCash(insertedCash2);

        System.out.println("После внесения наличных\n");
        departmentATM.getBalanceFromAllATM();

        departmentATM.restoreState();

        System.out.println("После восстановления состояния\n");
        departmentATM.getBalanceFromAllATM();
    }
}
