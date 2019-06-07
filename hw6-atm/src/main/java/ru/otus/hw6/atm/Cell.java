package ru.otus.hw6.atm;

public class Cell implements Comparable<Cell>, Cloneable {
    /**
     * Количество купюр
     */
    private int count;

    /**
     * Номинал
     */
    private final BillValue value;

    public Cell(BillValue value) {
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
    public BillValue getValue() {
        return value;
    }

    @Override
    public int compareTo(Cell item) {
        return Integer.compare(value.getValue(), item.getValue().getValue());
    }

    @Override
    public String toString() {
        return "Cell{" +
                "count=" + count +
                ", value=" + value +
                '}';
    }

    @Override
    public Cell clone() {
        Cell cell = new Cell(value);
        cell.addBill(count);
        return cell;
    }
}
