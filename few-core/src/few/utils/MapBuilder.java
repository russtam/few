package few.utils;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 18.12.11
 * Time: 3:52
 * To change this template use File | Settings | File Templates.
 */
public class MapBuilder<K, V> extends HashMap<K, V> {

    public MapBuilder add(K key,V value) {
        put(key, value);
        return this;
    }
}
