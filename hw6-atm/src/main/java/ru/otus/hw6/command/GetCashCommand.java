package ru.otus.hw6.command;

import ru.otus.hw6.ATM;
import ru.otus.hw6.BillValue;

import java.util.Map;

public class GetCashCommand implements Command<Map<BillValue, Integer>> {

    private final ATM atm;
    private final int sum;

    GetCashCommand(ATM atm, int sum) {
        this.atm = atm;
        this.sum = sum;
    }

    @Override
    public Map<BillValue, Integer> execute() {
        return atm.getCash(sum);
    }
}
