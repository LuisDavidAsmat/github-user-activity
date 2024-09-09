import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


public class Cache<K, V>
{
    private final ConcurrentHashMap<K, CacheEntry<V>> cacheMap = new ConcurrentHashMap<>();
    private final long expiryTimeInMillis;

    public Cache(long expiryTime, TimeUnit unit)
    {
        this.expiryTimeInMillis = unit.toMillis(expiryTime);

    }

    public void put(K key, V value)
    {
        cacheMap.put(key, new CacheEntry<>(
                value, System.currentTimeMillis() + expiryTimeInMillis));
        System.out.println("Cache put: " + key);
    }

    public V get(K key)
    {
        CacheEntry<V> entry = cacheMap.get(key);

        if (entry != null && entry.isNotExpired())
        {
            System.out.println("Cache hit: " + key);

            return entry.value;
        }

        cacheMap.remove(key);

        return null;
    }

    private record CacheEntry<V> (V value, long expiryTimeInMillis)
    {
        public boolean isNotExpired()
        {
            return System.currentTimeMillis() < expiryTimeInMillis;
        }
    }

//    private static class CacheEntry<V> {
//        private final V value;
//        private final long expiryTimeInMillis;
//
//        public CacheEntry(V value, long expiryTimeInMillis) {
//            this.value = value;
//            this.expiryTimeInMillis = expiryTimeInMillis + System.currentTimeMillis();
//        }
//
//        public V getValue() {
//            return value;
//        }
//
//        public boolean isNotExpired() {
//            return System.currentTimeMillis() < expiryTimeInMillis;
//        }
//    }
}