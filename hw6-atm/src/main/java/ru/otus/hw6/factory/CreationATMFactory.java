package ru.otus.hw6.factory;

import ru.otus.hw6.atm.ATM;
import ru.otus.hw6.atm.ATMImpl;
import ru.otus.hw6.atm.BillValue;

import java.util.List;
import java.util.Map;

public class CreationATMFactory {
    public static ATM createATM(List<BillValue> atmConfig, Map<BillValue, Integer> startBills, int number) {
        ATM atm = new ATMImpl(atmConfig, number);
        atm.addCash(startBills);

        return atm;
    }
}
