package few.common;

import javax.servlet.*;
import java.io.IOException;

/**
 * User: igor
 * Date: 02.01.12
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
