package ru.otus.hw6;

import ru.otus.hw6.memento.Memento;

import java.util.*;

import static ru.otus.hw6.DepartmentATM.getSumInPackBills;

public class ATMImpl implements ATM {

    /**
     * Ячейки под купюры
     */
    private final Set<Cell> cells;

    /**
     * Номинал купюры-ячейка
     */
    private final Map<BillValue, Cell> cellsMap = new HashMap<>();

    private final int number;

    public ATMImpl(List<BillValue> cellsConfig, int number) {
        cells = new TreeSet<>(Collections.reverseOrder());
        cellsConfig.forEach(cellValue -> {
            Cell cell = new Cell(cellValue);
            cells.add(cell);
            cellsMap.put(cellValue, cell);
        });
        this.number = number;
    }

    @Override
    public Map<BillValue, Integer> addCash(Map<BillValue, Integer> bills) {

        Map<BillValue, Integer> result = new HashMap<>();

        for(var bill : bills.entrySet()) {
            Cell cell = cellsMap.get(bill.getKey());
            if(cell != null) {
                cell.addBill(bill.getValue());
                result.put(bill.getKey(), bill.getValue());
            }
        }

        return result;
    }

    @Override
    public Map<BillValue, Integer> getCash(int sum) {
        Map<BillValue, Integer> result = new HashMap<>();

        int tmpSum = sum;

        for (Cell cell : cells) {

            if(getSumInPackBills(result) == sum) {
                break;
            }

            int countBill = tmpSum / cell.getValue().getValue();
            if(countBill > 0 && cell.getCount() > 0) {
                if(countBill > cell.getCount()) {
                    result.put(cell.getValue(), cell.getCount());
                    cell.getBill(cell.getCount());
                } else {
                    result.put(cell.getValue(), countBill);
                    cell.getBill(countBill);
                }
            }
        }

        if(getSumInPackBills(result) != sum) {
            addCash(result);
            return new HashMap<>();
        }

        return result;
    }

    @Override
    public Set<Cell> getCells() {
        return cells;
    }

    @Override
    public int getBalanceSum() {
        return cells.stream().mapToInt(cell -> cell.getCount()*cell.getValue().getValue()).sum();
    }

    @Override
    public void getBalance(Visitor visitor) {
        visitor.visitATM(this);
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public Memento saveState() {
        return new Memento(this);
    }

    @Override
    public ATM restoreState(Memento memento) {
        return memento.getState();
    }

    @Override
    public void setCells(Set<Cell> cells) {
        this.cells.clear();
        this.cellsMap.clear();

        cells.forEach(cell -> {
            this.cells.add(cell);
            cellsMap.put(cell.getValue(), cell);
        });
    }
}
