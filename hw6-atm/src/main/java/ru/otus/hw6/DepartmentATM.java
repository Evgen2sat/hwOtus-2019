package ru.otus.hw6;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DepartmentATM {

    private final List<ATM> atmList;

    DepartmentATM() {
        atmList = new ArrayList<>();
    }

    public ATMImpl createAtm(List<BillValue> atmConfig) {
        ATMImpl atm = new ATMImpl(atmConfig, atmList.size() + 1);
        atmList.add(atm);
        return atm;
    }

    public static int getSumInPackBills(Map<Integer, BillValue> bills) {
        return bills.entrySet().stream().mapToInt(item -> item.getKey()*item.getValue().getValue()).sum();
    }

    public void getBalanceFromAllATM() {
        Visitor visitor = new GrabberATMBalanceVisitor();
        atmList.forEach(atm -> atm.accept(visitor));
    }
}
