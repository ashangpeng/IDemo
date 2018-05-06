package me.duzhi.demo.hm;

public interface HMMap<K, V> {
    public void put(K k, V v);

    public V get(K k);

    public boolean remove(K k);

    public int size();

    public interface Entry<K, V> {
        public K getKey();
        public V getValue();
    }
}

