package ru.otus.hw6;

public class Cell implements Comparable<Cell> {
    /**
     * Количество купюр
     */
    private int count;

    /**
     * Номинал
     */
    private int value;

    public Cell(int value) {
        this.value = value;
    }

    /**
     * Получить количество купюр
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     * Добавить купюры
     * @param count
     */
    public void addBill(int count) {
        this.count += count;
    }

    /**
     * Получить купюры
     * @param count
     */
    public void getBill(int count) {
        this.count -= count;
    }

    /**
     * Получить номинал
     * @return
     */
    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(Cell item) {
        if(value == item.getValue()) {
            return 0;
        } else if(value < item.getValue()) {
            return -1;
        } else {
            return 1;
        }
    }
}
