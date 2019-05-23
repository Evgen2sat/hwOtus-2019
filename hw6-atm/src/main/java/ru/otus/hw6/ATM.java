package ru.otus.hw6;

import java.util.*;

public class ATM {
    private final Cell cell_50;
    private final Cell cell_100;
    private final Cell cell_200;
    private final Cell cell_500;
    private final Cell cell_1000;
    private final Cell cell_2000;
    private final Cell cell_5000;

    //private final Map<Integer, Cell> cells;
    private final Set<Cell> cells;

    public ATM() {
        cell_50 = new Cell(50);
        cell_100 = new Cell(100);
        cell_200 = new Cell(200);
        cell_500 = new Cell(500);
        cell_1000 = new Cell(1000);
        cell_2000 = new Cell(2000);
        cell_5000 = new Cell(5000);

        //cells = new TreeMap<>(Collections.reverseOrder());
//        cells.put(50, cell_50);
//        cells.put(100, cell_100);
//        cells.put(200, cell_200);
//        cells.put(500, cell_500);
//        cells.put(1000, cell_1000);
//        cells.put(2000, cell_2000);
//        cells.put(5000, cell_5000);

        cells = new HashSet<>();
        cells.add(cell_50);
        cells.add(cell_100);
        cells.add(cell_200);
        cells.add(cell_500);
        cells.add(cell_1000);
        cells.add(cell_2000);
        cells.add(cell_5000);
    }


    public int addCash(int sum) {
        int tmpSum = sum;
        int insertedSum = 0;

//        for(var cell : cells.entrySet()) {
//
//            if(insertedSum == sum) {
//                break;
//            }
//
//            int countBill = tmpSum / cell.getValue().getValue();
//            if(countBill > 0) {
//                cell.getValue().addBill(countBill);
//                insertedSum += countBill*cell.getValue().getValue();
//                tmpSum -= insertedSum;
//            }
//        }

        for(var cell : cells) {

            if(insertedSum == sum) {
                break;
            }

            int countBill = tmpSum / cell.getValue();
            if(countBill > 0) {
                cell.addBill(countBill);
                insertedSum += countBill*cell.getValue();
                tmpSum -= insertedSum;
            }
        }

        return insertedSum;
    }

    public int getCash(int sum) {
        int tmpSum = sum;
        int issuedSum = 0;

//        for (var cell : cells.entrySet()) {
//
//            if(issuedSum == sum) {
//                break;
//            }
//
//            int countBill = tmpSum / cell.getValue().getValue();
//            if(countBill > 0 && cell.getValue().getCount() > 0) {
//                if(countBill > cell.getValue().getCount()) {
//                    issuedSum += cell.getValue().getValue()*cell.getValue().getCount();
//                    cell.getValue().getBill(cell.getValue().getCount());
//                } else {
//                    issuedSum += cell.getValue().getValue()*countBill;
//                    cell.getValue().getBill(countBill);
//                }
//                tmpSum -= issuedSum;
//            }
//        }

        for (var cell : cells) {

            if(issuedSum == sum) {
                break;
            }

            int countBill = tmpSum / cell.getValue();
            if(countBill > 0 && cell.getCount() > 0) {
                if(countBill > cell.getCount()) {
                    issuedSum += cell.getValue()*cell.getCount();
                    cell.getBill(cell.getCount());
                } else {
                    issuedSum += cell.getValue()*countBill;
                    cell.getBill(countBill);
                }
                tmpSum -= issuedSum;
            }
        }

        if(issuedSum != sum) {
            addCash(issuedSum);
            return 0;
        }

        return issuedSum;
    }

    public int getBalance() {
        return cells.stream().mapToInt(cell -> cell.getCount() * cell.getValue()).sum();
    }
}