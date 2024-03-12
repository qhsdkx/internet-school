package by.andron.cache;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class TwoQCache {

    private final int capacity;
    private final LinkedHashMap<Long, Object> hotQueue;
    private final LinkedHashMap<Long, Object> coldQueue;

    public TwoQCache(int capacity){
        this.capacity = capacity;
        this.hotQueue = new LinkedHashMap<>(capacity/5, 0.75f, true);
        this.coldQueue = new LinkedHashMap<>(capacity - capacity/5, 0.75f, true);
    }

    public TwoQCache(){
        this.capacity = 20;
        this.hotQueue = new LinkedHashMap<>(capacity/5, 0.75f, true);
        this.coldQueue = new LinkedHashMap<>(capacity - capacity/5, 0.75f, true);
    }

    public Object get(Long key){
        Object value = hotQueue.get(key);
        if(value == null){
            value = coldQueue.get(key);
            if(value != null){
                hotQueue.put(key, value);
                coldQueue.remove(key);
             }
        }
        return value;
    }

    public List<Object> getAllFromHotQueue(){
        return hotQueue.keySet().stream().map(this::get).toList();
    }

    public List<Object> getAllFromColdQueue(){
        return coldQueue.keySet().stream().map(this::get).toList();
    }

    public void put(Long key, Object value){
        if(hotQueue.containsKey(key) || coldQueue.containsKey(key)){
            hotQueue.put(key, value);
            coldQueue.remove(key);
        } else {
            hotQueue.put(key, value);
            if(hotQueue.size() > capacity){
                Map.Entry<Long, Object> entryToRemove = hotQueue.entrySet().iterator().next();
                hotQueue.remove(entryToRemove.getKey());
                coldQueue.put(entryToRemove.getKey(), entryToRemove.getValue());
            }
        }
    }

    public void delete(Long key){
        hotQueue.remove(key);
        coldQueue.remove(key);
    }

    public boolean containsKey(Long key){
        return hotQueue.containsKey(key) || coldQueue.containsKey(key);
    }

}
