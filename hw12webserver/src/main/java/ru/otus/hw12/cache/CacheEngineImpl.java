package ru.otus.hw12.cache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {
    private static final int TIME_THRESHOLD_MS = 5;

    private final int maxElements;
    private final long lifeTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<CacheItem<V>>> cacheItems = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    public CacheEngineImpl(int maxElements, long lifeTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 || isEternal;
    }

    @Override
    public void put(K key, V value) {
        if(cacheItems.size() == maxElements) {
            K item = cacheItems.keySet().iterator().next();
            cacheItems.remove(item);
        }

        cacheItems.put(key, new SoftReference<>(new CacheItem<>(value)));

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.get().getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
        }
    }

    @Override
    public V get(K key, Function<K, V> getFunction) {
        SoftReference<CacheItem<V>> cacheItem = cacheItems.get(key);

        if(cacheItem == null) {
            miss++;
            V dbItem = getFunction.apply(key);
            if(dbItem != null) {
                put(key, dbItem);
                cacheItem = cacheItems.get(key);
            }
        } else {
            hit++;
        }

        return cacheItem != null ? cacheItem.get().getValue() : null;
    }

    @Override
    public int getHitCount() {
        return hit;
    }

    @Override
    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key, Function<SoftReference<CacheItem<V>>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                SoftReference<CacheItem<V>> item = cacheItems.get(key);
                if (item == null || isT1BeforeT2(timeFunction.apply(item), System.currentTimeMillis())) {
                    cacheItems.remove(key);
                    this.cancel();
                }
            }
        };
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }
}
