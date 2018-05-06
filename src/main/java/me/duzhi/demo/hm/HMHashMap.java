package me.duzhi.demo.hm;

public class HMHashMap<K, V> implements HMMap<K, V> {
    //** 默认HashTable大小
    private static int defaultLength = 16;
    //** HashTable 大小
    private int length;

    private static double defaultHolder = 0.75;//加载因子
    private double holder;

    private Entry<K, V>[] entries;
    private int entriesLength = 0;
    private int size;

    public HMHashMap(int length, double holder) {
        this.length = length;
        this.holder = holder;
        entries = new Entry[length];
    }

    public HMHashMap() {
        this(defaultLength, defaultHolder);
    }

    public void put(K k, V v) {
        if (entriesLength > length * holder) {
            resize();
        }
        int index = getIndex(k);
        Entry<K, V> entry = entries[index];
        if (entry == null) {
            entry = newEntry(k, v, null);
            entriesLength++;
        } else {
            entry = newEntry(k, v, entry);
        }
        entries[index] = entry;
        size++;
    }

    private void resize() {
        //临时保存
        Entry<K, V> cacheEntry = cacheEntry();
        //重新整理
        reEntries();
        put(cacheEntry);
    }

    private void reEntries() {
        entries = new Entry[length * 2];
        entriesLength = 0;
        length = length * 2;
        size = 0;
    }

    private void put(Entry<K, V> cacheEntry) {
        put(cacheEntry.getKey(), cacheEntry.getValue());
        if (cacheEntry.next != null) {
            put(cacheEntry.next);
        }
    }

    private Entry<K, V> cacheEntry() {
        Entry<K, V> cacheEntry = null;//临时存储所有entry;
        for (int i = 0; i < entries.length; i++) {
            Entry<K, V> entry = entries[i];
            if (entry != null) {
                if (cacheEntry == null)
                    cacheEntry = entry;
                else {
                    entryNext(cacheEntry, entry);
                    cacheEntry = entry;
                }
            }
        }
        return cacheEntry;
    }

    private void entryNext(Entry<K, V> cacheEntry, Entry<K, V> entry) {
        if (entry.next == null)
            entry.next = cacheEntry;
        else
            entryNext(cacheEntry, entry.next);
    }

    private Entry<K, V> newEntry(K k, V v, Entry<K, V> entry) {
        Entry entry1 = new Entry<K, V>(k, v);
        entry1.setNext(entry);
        return entry1;
    }

    private int getIndex(K k) {
        int index = k.hashCode() % length;
        return index < 0 ? -index : index;
    }

    public V get(K k) {
        int index = getIndex(k);
        Entry<K, V> entry = entries[index];
        Entry<K, V> currentEntry = getEntryByKey(k, entry);
        return currentEntry == null ? null : currentEntry.getValue();
    }

    private Entry<K, V> getEntryByKey(K k, Entry<K, V> entry) {
        if (k == entry.getKey() || k.equals(entry.getKey())) {
            return entry;
        } else if (entry.next != null)
            return getEntryByKey(k, entry.next);
        return null;
    }

    public boolean remove(K k) {
        int index = getIndex(k);
        if (entries[index] == null) {
            return false;
        } else {
            return replaceEntryByKey(k, entries[index], null);
        }
    }

    private boolean replaceEntryByKey(K k, Entry<K, V> entry, Entry<K, V> lastEntry) {
        if (k == entry.getKey() || k.equals(entry.getKey())) {
            if (lastEntry == null) {
                int index = getIndex(k);
                entries[index] = entry.next;
            } else {
                lastEntry.next = entry.next;
            }
            size--;
            return true;
        } else if (entry.next != null)
            return replaceEntryByKey(k, entry.next, entry);
        return false;
    }

    public int size() {
        return size;
    }

    public static class Entry<K, V> implements HMMap.Entry<K, V> {

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        private K key;
        private V value;
        private Entry<K, V> next;


        public void setKey(K key) {
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Entry<K, V> getNext() {
            return next;
        }

        public void setNext(Entry<K, V> next) {
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
