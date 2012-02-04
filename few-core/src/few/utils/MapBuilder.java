package few.utils;

import java.util.HashMap;

/**
 * User: igor
 * Date: 18.12.11
 */
public class MapBuilder<K, V> extends HashMap<K, V> {

    public MapBuilder add(K key,V value) {
        put(key, value);
        return this;
    }
}
