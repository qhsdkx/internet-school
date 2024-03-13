package by.andron.cache.impl;

import by.andron.cache.Cache;

import java.util.LinkedHashMap;
import java.util.List;

public class LRUCache implements Cache<Long, Object> {

    private final int capacity;
    private final LinkedHashMap<Long, Object> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true);
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

    public List<Object> getAll(){
        return cache.keySet().stream().map(this::get).toList();
    }

    @Override
    public void put(Long key, Object object) {
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
