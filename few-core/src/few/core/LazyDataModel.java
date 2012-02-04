package few.core;

import few.Context;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * User: gerbylev
 * Date: 29.11.11
 */
public class LazyDataModel implements Map<String, Object> {

    Map<String, DispatcherMap.ModelBean> description;
    Map<String, Object> instances;

    public LazyDataModel(DispatcherMap map) {
        this.description = map.getModels();
        instances = new HashMap<String, Object>();
    }

    public Object get(Object key) {
        Object o = instances.get(key);
        if( o == null ) {
            DispatcherMap.ModelBean desc = description.get(key);
            if( desc == null ) {
                //throw new IllegalArgumentException("no such modelBean");
                return null;
            }

            o = ActionInvoker.invokeModelMethod(desc.name, desc.getMethod(), Context.get().getRequest(), Context.get().getResponse());

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
        if( description.containsKey(key) )
            return true;
        return instances.containsKey(key);
    }
    public Set<String> keySet() {
        return description.keySet();
    }
    public Object put(String key, Object value) {
        return instances.put(key, value);
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
