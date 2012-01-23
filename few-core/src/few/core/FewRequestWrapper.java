package few.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 23.01.12
 * Time: 8:41
 * To change this template use File | Settings | File Templates.
 */
public class FewRequestWrapper extends HttpServletRequestWrapper {

    public FewRequestWrapper(HttpServletRequest request, Map<String, String> vars) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        return super.getParameterValues(name);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Enumeration getParameterNames() {
        return super.getParameterNames();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Map getParameterMap() {
        return super.getParameterMap();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String getParameter(String name) {
        return super.getParameter(name);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Locale getLocale() {
        return super.getLocale();
    }
}
