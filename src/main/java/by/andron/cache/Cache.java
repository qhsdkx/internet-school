package by.andron.cache;

public interface Cache <K, V>{

    public V get(K key);

    public void put(K key, V object);

    public void delete(K key);

    public boolean containsKey(K key);

}
