package few.core;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 29.11.11
 * Time: 17:32
 * To change this template use File | Settings | File Templates.
 */
public class LazyDataModel implements Map<String, Object> {

    Map<String, DispatcherMap.ModelBeanDescription> description;
    Map<String, Object> instances;

    public LazyDataModel(DispatcherMap map) {
        this.description = map.getModels();
        instances = new HashMap<String, Object>();
    }

    public Object get(Object key) {
        Object o = instances.get(key);
        if( o == null ) {
            DispatcherMap.ModelBeanDescription desc = description.get(key);
            if( desc == null )
                throw new IllegalArgumentException("no such modelBean");

            try {
                o = desc.method.invoke(null);
            } catch (Throwable e) {
                if( e.getCause() != null )
                    throw new RuntimeException("can not create ModelBean " + key, e.getCause());
                else
                    throw new RuntimeException("can not create ModelBean " + key, e);
            }

            instances.put((String) key, o);
        }
        return o;
    }

    public int size() {
        return description.size();  //To change body of implemented methods use File | Settings | File Templates.
    }
    public boolean isEmpty() {
        return description.isEmpty();
    }
    public boolean containsKey(Object key) {
        return description.containsKey(key);
    }
    public Set<String> keySet() {
        return description.keySet();
    }

    public Object put(String key, Object value) {
        throw new UnsupportedOperationException();
    }
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }
    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }
    public void putAll(Map m) {
        throw new UnsupportedOperationException();
    }
    public void clear() {
        throw new UnsupportedOperationException();
    }
    public Collection<Object> values() {
        throw new UnsupportedOperationException();
    }
    public Set<Map.Entry<String, Object>> entrySet() {
        throw new UnsupportedOperationException();
    }
}
