package few.core;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * User: igor
 * Date: 22.01.12
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
