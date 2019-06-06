package ru.otus.hw6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentATM {

    private List<ATM> atmList;
    private final Map<ATM, Memento> mementoList;

    DepartmentATM() {
        atmList = new ArrayList<>();
        mementoList = new HashMap<>();
    }

//    public ATM createAtm(List<BillValue> atmConfig, Map<BillValue, Integer> startBills) {
//        ATM atm = new ATMImpl(atmConfig, atmList.size() + 1);
//        atm.addCash(startBills);
//        mementoList.put(atm, atm.saveState());
//        atmList.add(atm);
//        return atm;
//    }

    public DepartmentATM addATM(ATM atm) {
        mementoList.put(atm, atm.saveState());
        atmList.add(atm);

        return this;
    }

    public static int getSumInPackBills(Map<BillValue, Integer> bills) {
        return bills.entrySet().stream().mapToInt(item -> item.getKey().getValue()*item.getValue()).sum();
    }

    public void getBalanceFromAllATM() {
        Visitor visitor = new GrabberATMBalanceVisitor();
        atmList.forEach(atm -> atm.getBalance(visitor));
    }

    public void restoreState() {
        this.atmList.clear();

        mementoList.forEach((key, value) -> this.atmList.add(key.restoreState(value)));
    }
}
