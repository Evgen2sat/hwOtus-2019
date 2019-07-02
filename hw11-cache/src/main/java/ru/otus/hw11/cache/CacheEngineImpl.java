package ru.otus.hw11.cache;

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

    private final SoftReference<Map<K, CacheItem<V>>> cacheItems = new SoftReference<>(new LinkedHashMap<>());
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
        if(cacheItems.get().size() == maxElements) {
            K item = cacheItems.get().keySet().iterator().next();
            cacheItems.get().remove(item);
        }

        cacheItems.get().put(key, new CacheItem<>(value));

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
        }
    }

    @Override
    public V get(K key, Function<K, V> getFunction) {
        CacheItem<V> cacheItem = cacheItems.get().get(key);

        if(cacheItem == null) {
            miss++;
            V dbItem = getFunction.apply(key);
            if(dbItem != null) {
                put(key, dbItem);
                cacheItem = cacheItems.get().get(key);
            }
        } else {
            hit++;
        }

        return cacheItem != null ? cacheItem.getValue() : null;
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

    private TimerTask getTimerTask(final K key, Function<CacheItem<V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                CacheItem<V> item = cacheItems.get().get(key);
                if (item == null || isT1BeforeT2(timeFunction.apply(item), System.currentTimeMillis())) {
                    cacheItems.get().remove(key);
                    this.cancel();
                }
            }
        };
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }
}
