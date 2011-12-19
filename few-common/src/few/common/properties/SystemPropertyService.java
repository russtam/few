package few.common.properties;

import few.common.BaseMyBatisServiceImpl;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 18.11.11
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
public class SystemPropertyService extends BaseMyBatisServiceImpl {

    private static SystemPropertyService service = new SystemPropertyService();
    public static SystemPropertyService get() {
        return service;
    }
    private SystemPropertyService() {
        load();
    }

    Map<String, SystemProperty> propertyMap;

    public String getProperty(String key) {
        load();
        SystemProperty value = propertyMap.get(key);
        if( value == null )
            return null;
        return value.value;
    }

    public void setProperty(String key, String value) {
        SystemProperty p = propertyMap.get(key);
        if( p == null ) {
            p = new SystemProperty(key, value);
            propertyMap.put(key, p);
        }
        p.value = value;

    }

    public List<SystemProperty> getProperties() {
        load();
        List<SystemProperty> list = new LinkedList<SystemProperty>(propertyMap.values());
        Collections.sort(list, new Comparator<SystemProperty>() {
            public int compare(SystemProperty o1, SystemProperty o2) {
                return o1.key.compareTo(o2.key);
            }
        });
        return list;
    }

    private void load() {
        SqlSession session = sqlMapper.openSession();
        try {
            List<SystemProperty> list = session.selectList("selectSystemProperties");

            propertyMap = new HashMap<String, SystemProperty>();
            for (Iterator<SystemProperty> iterator = list.iterator(); iterator.hasNext(); ) {
                SystemProperty property = iterator.next();
                propertyMap.put(property.key, property);
            }
        } finally {
            session.close();
        }
    }

    public void save() {
        SqlSession session = sqlMapper.openSession();
        try {
            session.delete("clearSystemProperties");

            for (Iterator<SystemProperty> iterator = propertyMap.values().iterator(); iterator.hasNext(); ) {
                SystemProperty property = iterator.next();
                session.insert("insertSystemProperty", property);
            }

            session.commit();
        } finally {
            session.close();
        }
    }

    public void clear() {
        propertyMap.clear();
    }

}
