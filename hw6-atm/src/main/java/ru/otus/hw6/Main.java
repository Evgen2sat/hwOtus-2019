package ru.otus.hw6;

import ru.otus.hw6.atm.ATM;
import ru.otus.hw6.atm.BillValue;
import ru.otus.hw6.atm.DepartmentATM;
import ru.otus.hw6.command.GetCashCommand;
import ru.otus.hw6.factory.CreationATMFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        ATM atm1 = CreationATMFactory.createATM(atmConfig1, insertedCash1, 1);
        ATM atm2 = CreationATMFactory.createATM(atmConfig2, insertedCash2, 2);
        ATM atm3 = CreationATMFactory.createATM(atmConfig3, insertedCash3, 3);
        ATM atm4 = CreationATMFactory.createATM(atmConfig4, insertedCash4, 4);

        departmentATM
                .addATM(atm1)
                .addATM(atm2)
                .addATM(atm3)
                .addATM(atm4);

        System.out.println("Начальное состояние");
        System.out.println(atm1.getCells());

        GetCashCommand getCashCommand1 = new GetCashCommand(atm1, 500);
        getCashCommand1.execute();

        System.out.println("\nПосле получения наличных");
        System.out.println(atm1.getCells());

        departmentATM.restoreState();

        System.out.println("\nПосле восстановления состояния");
        System.out.println(atm1.getCells());

        getCashCommand1.execute();

        System.out.println("\nПосле получения наличных");
        System.out.println(atm1.getCells());

        departmentATM.restoreState();

        System.out.println("\nПосле восстановления состояния");
        System.out.println(atm1.getCells());
    }
}
