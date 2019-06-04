package ru.otus.hw6;

import ru.otus.hw6.memento.Memento;

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

    public ATMImpl createAtm(List<BillValue> atmConfig, Map<BillValue, Integer> startBills) {
        ATMImpl atm = new ATMImpl(atmConfig, atmList.size() + 1);
        atm.addCash(startBills);
        mementoList.put(atm, atm.saveState());
        atmList.add(atm);
        return atm;
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

        mementoList.entrySet().forEach(item -> this.atmList.add(item.getKey().restoreState(item.getValue())));
    }
}
