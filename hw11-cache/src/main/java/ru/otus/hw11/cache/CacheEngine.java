package ru.otus.hw11.cache;

import java.util.function.Function;

public interface CacheEngine<K, V> {

    void put(K key, V value);

    V get(K key, Function<K, V> getFunction);

    int getHitCount();

    int getMissCount();

    void dispose();
}
