package ru.otus.hw6;

import java.util.Map;
import java.util.Set;

public interface ATM {
    Map<Integer, BillValue> addCash(Map<Integer, BillValue> bills);

    Map<Integer, BillValue> getCash(int sum);

    Set<Cell> getBalance();

    int getBalanceSum();

    void accept(Visitor visitor);

    int getNumber();
}
