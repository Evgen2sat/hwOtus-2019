package ru.otus.hw6;

import java.util.*;

public class ATM {

    /**
     * Ячейки под купюры
     */
    private final Set<Cell> cells;

    /**
     * Номинал купюры-ячейка
     */
    private final Map<BillValue, Cell> cellsMap = new HashMap<>();

    public ATM(List<BillValue> cellsConfig) {
        cells = new TreeSet<>(Collections.reverseOrder());
        cellsConfig.forEach(cellValue -> {
            Cell cell = new Cell(cellValue);
            cells.add(cell);
            cellsMap.put(cellValue, cell);
        });
    }


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

    public Set<Cell> getBalance() {
        return cells;
    }

    public int getBalanceSum() {
        return cells.stream().mapToInt(cell -> cell.getCount()*cell.getValue().getValue()).sum();
    }

    public static int getSumInPackBills(Map<Integer, BillValue> bills) {
        return bills.entrySet().stream().mapToInt(item -> item.getKey()*item.getValue().getValue()).sum();
    }
}
