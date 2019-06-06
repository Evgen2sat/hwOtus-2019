package ru.otus.hw6.command;

import ru.otus.hw6.atm.ATM;
import ru.otus.hw6.atm.BillValue;

import java.util.Map;

public class GetCashCommand implements Command<Map<BillValue, Integer>> {

    private final ATM atm;
    private final int sum;

    public GetCashCommand(ATM atm, int sum) {
        this.atm = atm;
        this.sum = sum;
    }

    @Override
    public Map<BillValue, Integer> execute() {
        return atm.getCash(sum);
    }
}
