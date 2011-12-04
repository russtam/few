package few.core;

import few.ActionResponse;
import few.Context;
import few.MyURL;
import few.support.FreemarkerService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 31.10.11
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
public class Dispatcher implements Filter{
    public static final String BASE_SERVLET_PATH = "/";
    public static final String BASE_RESOURCE_PATH = "/pages/";
    public static final String DEFAULT_PAGE = "about";

    ServletContext servletContext;
    DispatcherMap config;
    DispatcherSelector selector;
    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
        FreemarkerService.get().initFreemarker(servletContext);
        config = DispatcherMap.build(servletContext, Thread.currentThread().getContextClassLoader());
        selector = new DispatcherSelector(config);
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

            // 1. validate
            // 2. get page filename
            // 3. check exists
            // 4. forward

            // 1. process action
            int i = uri.indexOf("/", BASE_SERVLET_PATH.length());
            i = i == -1 ? uri.length() : i;
            String action = uri.substring(BASE_SERVLET_PATH.length(), i);

            Enumeration<String> e = request.getParameterNames();
            Set<String> parameters = new HashSet<String>();
            while (e.hasMoreElements()) {
                parameters.add(e.nextElement());
            }

            DispatcherMap.ActionMethodDescription am = selector.selectMethod(action, parameters);
            ActionResponse ar;
            if( am != null ) {
                if( authorized(am.authorized_roles) )
                    ar = ActionInvoker.invokeActionMethod(am, request, response);
                else
                    ar = unauthorized_redirect(am.unauthorized_redirect);

            } else {
                DispatcherMap.ActionDescription ad = selector.selectAction(action);
                if( ad == null || authorized(ad.authorized_roles) )
                    ar = ActionResponse._default();
                else
                    ar = unauthorized_redirect(am.unauthorized_redirect);
            }

            if( ar.getResponse_type() == ActionResponse.DEFAULT )
                ar = ActionResponse.view(action);

            String ftl = null;
            if( ar.getResponse_type() == ActionResponse.FORWARD ) {
                ftl= BASE_RESOURCE_PATH + ar.getKey() + ".ftl";
                if( servletContext.getResource(ftl) == null ) {
                    ar = ActionResponse.error(404);
                }
            }

            switch(ar.getResponse_type()) {
                case ActionResponse.FORWARD:
                    processTemplate(ftl, request, response);
                    break;
                case ActionResponse.REDIRECT:
                    response.sendRedirect(ar.getKey());
                    break;
                case ActionResponse.ACTION:
                    throw new Error("not implemented yet");
                case ActionResponse.ERROR:
                    response.sendError(ar.getError_code());
                case ActionResponse.JSON:
                    response.getOutputStream().print(ar.getKey());
                    response.getOutputStream().close();
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

        FreemarkerService.get().processTemplate(template, response.getWriter());
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String uri = ((HttpServletRequest)request).getRequestURI();
        if( uri.startsWith("/static") ) {
            filterChain.doFilter(request, response);
        } else
            service((HttpServletRequest)request, (HttpServletResponse) response);
    }

    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
