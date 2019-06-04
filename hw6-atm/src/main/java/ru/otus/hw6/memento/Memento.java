package ru.otus.hw6.memento;

import ru.otus.hw6.ATM;
import ru.otus.hw6.ATMImpl;

import java.util.ArrayList;
import java.util.List;

public class Memento {
    private final ATM atm;

    public Memento(ATM atm) {
        this.atm = atm;
    }

    public ATM getState() {
        return atm;
    }
}
