package ru.otus.hw6;

import java.util.Map;
import java.util.Set;

public interface ATM {
    Map<BillValue, Integer> addCash(Map<BillValue, Integer> bills);

    Map<BillValue, Integer> getCash(int sum);

    Set<Cell> getCells();

    int getBalanceSum();

    void getBalance(Visitor visitor);

    int getNumber();

    Memento saveState();

    ATM restoreState(Memento memento);

    void setCells(Set<Cell> cells);
}
