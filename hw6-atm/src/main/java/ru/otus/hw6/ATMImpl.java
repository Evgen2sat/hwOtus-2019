package ru.otus.hw6;

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

    private final int number;

    private final Map<BillValue, Cell> cellsMap = new HashMap<>();

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
    public Map<Integer, BillValue> addCash(Map<Integer, BillValue> bills) {

        Map<Integer, BillValue> result = new HashMap<>();

        for(var bill : bills.entrySet()) {
            Cell cell = cellsMap.get(bill.getValue());
            if(cell != null) {
                cell.addBill(bill.getKey());
                result.put(bill.getKey(), bill.getValue());
            }
        }

        return result;
    }

    @Override
    public Map<Integer, BillValue> getCash(int sum) {
        Map<Integer, BillValue> result = new HashMap<>();

        int tmpSum = sum;

        for (Cell cell : cells) {

            if(getSumInPackBills(result) == sum) {
                break;
            }

            int countBill = tmpSum / cell.getValue().getValue();
            if(countBill > 0 && cell.getCount() > 0) {
                if(countBill > cell.getCount()) {
                    result.put(cell.getCount(), cell.getValue());
                    cell.getBill(cell.getCount());
                } else {
                    result.put(countBill, cell.getValue());
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
    public Set<Cell> getBalance() {
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
}
