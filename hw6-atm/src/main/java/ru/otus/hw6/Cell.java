package ru.otus.hw6;

public class Cell {
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
}
