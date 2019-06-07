package ru.otus.hw6.atm;

public enum BillValue {
    Value_50(50),
    Value_100(100),
    Value_200(200),
    Value_500(500),
    Value_1000(1000),
    Value_2000(2000),
    Value_5000(5000);

    private int value;

    BillValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
