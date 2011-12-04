package few.utils;

import com.sun.deploy.util.Property;
import few.core.LazyDataModel;
import freemarker.template.utility.StringUtil;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 03.12.11
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class JSONRenderer {

    public static class SampleBean {
        private String field1;
        private String field2;

        public String getField1() {
            return field1;
        }

        public String getField2() {
            return field2;
        }
    }

    public static void main(String[] args)  {
        SampleBean sb = new SampleBean();
        sb.field1 = "fff1";
        sb.field2 = "fff222";

        System.out.println(buildJSON(sb));
    }

    // TODO : validate this for performance issues
    public static String buildJSON(Object model) {
        StringBuilder builder = new StringBuilder();

        visitObject(model, builder);

        return builder.toString();
    }


    private static void visitObject(Object o, StringBuilder sb) {
        sb.append("{");

        if( o instanceof LazyDataModel ) {
            Map m = (Map) o;
            Set<String> es = m.keySet();
            for (Iterator<String> iterator = es.iterator(); iterator.hasNext(); ) {
                String s = iterator.next();
                visitField(s, m.get(s), sb);
            }
        } else if( o instanceof Map) {
            Set<Map.Entry> es = ((Map)o).entrySet();
            for (Iterator<Map.Entry> iterator = es.iterator(); iterator.hasNext(); ) {
                Map.Entry entry = iterator.next();
                visitField(entry.getKey().toString(), entry.getValue(), sb);
            }
        } else {
            try {
                BeanInfo bi = Introspector.getBeanInfo(o.getClass());
                PropertyDescriptor pds[] = bi.getPropertyDescriptors();
                for (int i = 0; i < pds.length; i++) {
                    PropertyDescriptor pd = pds[i];

                    if( !pd.getReadMethod().getDeclaringClass().getName().startsWith("java") ) {
                        visitField(pd.getName(), pd.getReadMethod().invoke(o), sb);

                        if( i != pds.length - 1 )
                            sb.append(",");
                    }
                }
            } catch (Exception e) {
                log.log(Level.SEVERE, "error JSON Rendering for class " + o.getClass() + ", instance " + o.toString(), e);
            }
        }

        sb.append("}");
    }

    private static void visitField(String name, Object value, StringBuilder sb) {
        sb.append("\"").append(name).append("\":");
        visitValue(value, sb);
    }

    private static void visitValue(Object value, StringBuilder sb) {
        if( value == null ) {
            sb.append("null");
        } else if( value instanceof Boolean ) {
            sb.append(((Boolean)value)?"true":"false");
        } else if( value instanceof String ) {
            sb.append("\"").append(StringUtil.javaScriptStringEnc((String) value)).append("\"");
        } else if( value instanceof Number ) {
            sb.append(value);
        } else if( value.getClass().isArray() || value instanceof Collection) {
            sb.append("[");
            if( value.getClass().isArray() ) {
                Object[] arr = (Object[]) value;
                for (int i = 0; i < arr.length; i++) {
                    Object o = arr[i];
                    visitValue(o, sb);
                    if( i != arr.length - 1 )
                        sb.append(",");
                }
            } else
            if( value instanceof Collection ) {
                Collection c = (Collection) value;
                for (Iterator iterator = c.iterator(); iterator.hasNext(); ) {
                    Object o = iterator.next();
                    visitValue(o, sb);
                    if( iterator.hasNext() )
                        sb.append(",");
                }
            }

            sb.append("]");
        } else {
            visitObject(value, sb);
        }
    }


    static Logger log = Logger.getLogger(JSONRenderer.class.getName());
}
