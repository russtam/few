package few.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * User: igor
 * Date: 23.01.12
 */
public class FewRequestWrapper extends HttpServletRequestWrapper {

    Map<String, String[]> parameters;
    Enumeration<String> names;

    public FewRequestWrapper(HttpServletRequest request, Map<String, String> vars) {
        super(request);

        parameters = new HashMap<String, String[]>();
        parameters.putAll(super.getParameterMap());

        for (Map.Entry<String, String> e : vars.entrySet()) {
            parameters.put(e.getKey(), new String[] {e.getValue()});
        }
        parameters = Collections.unmodifiableMap(parameters);
    }

    @Override
    public String[] getParameterValues(String name) {
        return parameters.get(name);
    }

    @Override
    public Enumeration getParameterNames() {
        final Iterator<String> i = parameters.keySet().iterator();
        return new Enumeration<String>() {
            public boolean hasMoreElements() {
                return i.hasNext();
            }

            public String nextElement() {
                return i.next();
            }
        };
    }

    @Override
    public Map getParameterMap() {
        return parameters;
    }

    @Override
    public String getParameter(String name) {
        String ret[] = parameters.get(name);
        if( ret != null )
            if( ret.length > 0)
                return ret[0];
            else
                return "";
        return null;
    }

    @Override
    public Locale getLocale() {
        return super.getLocale();
    }
}
