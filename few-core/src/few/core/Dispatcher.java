package few.core;

import few.ActionResponse;
import few.Context;
import few.MyURL;
import few.routing.Routing;
import few.services.FreemarkerService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 31.10.11
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
public class Dispatcher implements Filter{
    public static final String BASE_SERVLET_PATH = "/";
    public static final String BASE_PAGE_PATH = "/pages/";
    public static final String BASE_RESOURCE_PATH = "/static";

    ServletContext servletContext;
    DispatcherMap config;
    Routing routing;
    FreemarkerService freemarker;

    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
        Initializer.init(servletContext);
        freemarker = ServiceRegistry.get(FreemarkerService.class);
        config = DispatcherMap.build(servletContext, Thread.currentThread().getContextClassLoader());
    }

    public ServletContext context() {
        return servletContext;
    }

    public RequestDispatcher dispatcher(String path) {
        return servletContext.getRequestDispatcher(path);
    }


    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uri = request.getRequestURI();

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

    private ActionResponse unauthorized_redirect(String unauthorized_redirect) {
        if( unauthorized_redirect == null || unauthorized_redirect.isEmpty() )
            unauthorized_redirect = "/login";
        return ActionResponse.redirect(new MyURL(unauthorized_redirect).p("redirect", Context.get().getRequest().getRequestURI()));
    }

    private boolean authorized(String[] authorized_roles) {
        if( authorized_roles == null )
            return true;
        for (int i = 0; i < authorized_roles.length; i++) {
            String authorized_role = authorized_roles[i];
            if(Context.get().isUserInRole(authorized_role))
                return true;
        }
        return false;
    }


    public void processTemplate(String template, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Prepare the HTTP response:
        // - Set the MIME-type and the charset of the output.
        //   Note that the charset should be in sync with the output_encoding setting.
        response.setContentType("text/html; charset=UTF-8");
        // - Prevent browser or proxy caching the page.
        //   Note that you should use it only for development and for interactive
        //   pages, as it significantly slows down the Web site.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");

        freemarker.processTemplate(template, response.getWriter());
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String uri = ((HttpServletRequest)request).getRequestURI();
        if( uri.startsWith(BASE_RESOURCE_PATH) || uri.equals("/favicon.ico") || uri.equals("/robots.txt")) {
            filterChain.doFilter(request, response);
        } else
            service((HttpServletRequest)request, (HttpServletResponse) response);
    }

    public void destroy() {
        Initializer.fini();
    }
}
