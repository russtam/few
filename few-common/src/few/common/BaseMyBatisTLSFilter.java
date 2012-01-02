package few.common;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 02.01.12
 * Time: 20:27
 * To change this template use File | Settings | File Templates.
 */
public class BaseMyBatisTLSFilter implements Filter{
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } finally {
            BaseMyBatisServiceImpl.onThreadStop();
        }
    }

    public void destroy() {
    }
}
