package ru.otus.hw6.memento;

import ru.otus.hw6.atm.ATM;
import ru.otus.hw6.atm.Cell;

import java.util.*;
import java.util.stream.Collectors;

public class Memento {
    private final ATM atm;
    private final Set<Cell> cells = new TreeSet<>(Collections.reverseOrder());

    public Memento(ATM atm) {
        this.atm = atm;
        cells.addAll(getCopyCells(this.atm.getCells()));
    }

    public void restoreState() {
        atm.setCells(getCopyCells(cells));
    }

    private Set<Cell> getCopyCells(Set<Cell> cells) {
        return cells.stream().map(Cell::clone).collect(Collectors.toSet());
    }
}
