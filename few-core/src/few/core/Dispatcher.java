package few.core;

import few.Context;
import few.routing.RouteProcessor;
import few.services.FreemarkerService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 31.10.11
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
public class Dispatcher implements Filter{
    public static final String BASE_SERVLET_PATH = "/";

    ServletContext servletContext;
    DispatcherMap config;
    RouteProcessor routing;
    FreemarkerService freemarker;

    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
        Initializer.init(servletContext);
        freemarker = ServiceRegistry.get(FreemarkerService.class);
        config = DispatcherMap.build(servletContext, Thread.currentThread().getContextClassLoader());

        routing = new RouteProcessor();
    }

    public ServletContext context() {
        return servletContext;
    }

    public RequestDispatcher dispatcher(String path) {
        return servletContext.getRequestDispatcher(path);
    }


    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            Context.init(request, response, servletContext, config);

            if( request.getMethod().equals("GET") ) {
                routing.processGetRoute(request, response);
            } else
            if( request.getMethod().equals("POST")) {
                routing.processPostRoute(request, response);
            } else
            {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            }

        } finally {
            Context.fini();
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        String uri = ((HttpServletRequest)request).getRequestURI();
//        if( uri.startsWith(BASE_RESOURCE_PATH) || uri.equals("/favicon.ico") || uri.equals("/robots.txt")) {
//            filterChain.doFilter(request, response);
//        } else
        service((HttpServletRequest)request, (HttpServletResponse) response);
    }

    public void destroy() {
        Initializer.fini();
    }
}
