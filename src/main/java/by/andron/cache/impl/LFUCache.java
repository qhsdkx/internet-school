package by.andron.cache.impl;


import by.andron.cache.Cache;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LFUCache implements Cache {

    private static final class Node {

        Object value;
        int useCount;
        long lastGetTime;

        public Node(Object value, int useCount) {
            this.value = value;
            this.useCount = useCount;
        }

    }

    private final int capacity;

    private final Map<Long, Node> cache;

    public LFUCache(@NotNull int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
    }

    @Override
    public Object get(Long key) {
        if (!containsKey(key)) {
            return null;
        }

        cache.get(key).useCount++;
        cache.get(key).lastGetTime = System.nanoTime();
        return cache.get(key).value;
    }

    @Override
    public void put(Long key, Object object) {
        if (containsKey(key)) {
            cache.get(key).value = object;
        }
        if (cache.size() == capacity) {
            removeMinUsedNode();
        }
        Node node = new Node(object, 0);
        node.lastGetTime = System.nanoTime();
        cache.put(key, node);
    }

    @Override
    public void delete(Long key) {
        cache.remove(key);
    }

    @Override
    public boolean containsKey(Long key) {
        return cache.containsKey(key);
    }

    private void removeMinUsedNode() {
        int minCount = Integer.MAX_VALUE;
        long currTime = System.nanoTime();
        Long minKey = 0L;
        for (Long key : cache.keySet()) {
            Node node = cache.get(key);
            if (node.useCount < minCount || (node.useCount == minCount && node.lastGetTime < currTime)) {
                minKey = key;
                minCount = node.useCount;
                currTime = node.lastGetTime;
            }
        }
        cache.remove(minKey);
    }

}