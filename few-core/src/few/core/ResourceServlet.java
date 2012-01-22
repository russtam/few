package few.core;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 22.01.12
 * Time: 22:14
 * To change this template use File | Settings | File Templates.
 */
public class ResourceServlet implements Servlet{
    public void init(ServletConfig config) throws ServletException {
    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;

        // forward to default resource servlet
        req.getRequestDispatcher(request.getRequestURI()).forward(req, res);
    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {
    }
}
