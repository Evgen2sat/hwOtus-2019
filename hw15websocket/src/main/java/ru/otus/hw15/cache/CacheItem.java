package ru.otus.hw15.cache;

public class CacheItem<T> {
    private T value;
    private long creationTime;

    public CacheItem(T value) {
        this.value = value;
        this.creationTime = System.currentTimeMillis();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }
}
