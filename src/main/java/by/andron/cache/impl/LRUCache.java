package by.andron.cache.impl;

import by.andron.cache.Cache;

import java.util.LinkedHashMap;

public class LRUCache implements Cache<Long, Object> {

    private final int capacity;
    private final LinkedHashMap<Long, Object> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity);
    }

    @Override
    public Object get(Long key) {
        if(containsKey(key)){
            Object value = cache.get(key);
            cache.put(key, value);
            return value;
        }
        return null;
    }

    @Override
    public void put(Long key, Object object) {
        if(cache.size() == capacity){
            Long firstKey = cache.keySet().iterator().next();
            cache.remove(firstKey);
        }
        cache.put(key, object);
    }

    @Override
    public void delete(Long key) {
        cache.remove(key);
    }

    @Override
    public boolean containsKey(Long key) {
        return cache.containsKey(key);
    }

}
