package ru.otus.hw6.atm;

import ru.otus.hw6.memento.Memento;
import ru.otus.hw6.visitor.GrabberATMBalanceVisitor;
import ru.otus.hw6.visitor.Visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentATM {

    private List<ATM> atmList;
    private final Map<ATM, Memento> mementoList;

    public DepartmentATM() {
        atmList = new ArrayList<>();
        mementoList = new HashMap<>();
    }

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
        mementoList.forEach(ATM::restoreState);
    }
}
