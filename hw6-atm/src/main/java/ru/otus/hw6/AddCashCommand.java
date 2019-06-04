package ru.otus.hw6;

import java.util.Map;

public class AddCashCommand implements Command<Map<BillValue, Integer>> {

    private final ATM atm;
    private final Map<BillValue, Integer> bills;

    AddCashCommand(ATM atm, Map<BillValue, Integer> bills) {
        this.atm = atm;
        this.bills = bills;
    }

    @Override
    public Map<BillValue, Integer> execute() {
        return atm.addCash(bills);
    }
}
