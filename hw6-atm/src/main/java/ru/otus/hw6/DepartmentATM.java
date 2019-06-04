package ru.otus.hw6;

import ru.otus.hw6.memento.Memento;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DepartmentATM {

    private List<ATM> atmList;
    private final List<Memento> mementoList;

    DepartmentATM() {
        atmList = new ArrayList<>();
        mementoList = new ArrayList<>();
    }

    public ATMImpl createAtm(List<BillValue> atmConfig, Map<BillValue, Integer> startBills) {
        ATMImpl atm = new ATMImpl(atmConfig, atmList.size() + 1);
        atm.addCash(startBills);
        mementoList.add(atm.saveState());
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
        List<ATM> tmpAtmList = new ArrayList<>();
        for(Memento memento : mementoList) {
            tmpAtmList.add(memento.getState());
        }

        this.atmList = tmpAtmList;
    }
}
