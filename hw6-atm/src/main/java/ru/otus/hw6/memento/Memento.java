package ru.otus.hw6.memento;

import ru.otus.hw6.atm.ATM;
import ru.otus.hw6.atm.Cell;

import java.util.*;

public class Memento {
    private final ATM atm;
    private final Set<Cell> cells = new TreeSet<>(Collections.reverseOrder());

    public Memento(ATM atm) {
        this.atm = atm;

        atm.getCells().forEach(cell -> cells.add(new Cell(cell.getValue()) { {addBill(cell.getCount());} }));
    }

    public ATM getState() {
        atm.setCells(cells);
        return atm;
    }
}
