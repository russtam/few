package few.core;

import few.ActionResponse;
import few.Context;
import few.routing.GetRoute;
import few.routing.PostRoute;
import few.services.FreemarkerService;
import few.services.Routing;

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
    Routing routing;
    FreemarkerService freemarker;

    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
        freemarker = ServiceRegistry.get(FreemarkerService.class);
        config = DispatcherMap.build(servletContext, Thread.currentThread().getContextClassLoader());
        routing = ServiceRegistry.get(Routing.class);
    }

    public ServletContext context() {
        return servletContext;
    }


    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            Routing.SelectedRoute sr = routing.selectRoute(request);
            if( sr == null ) {
                response.sendError(404, "route not found");
                return;
            }

            FewRequestWrapper fw = new FewRequestWrapper(request, sr.getVars());

            Context.init(fw, response, servletContext, config);

            if( sr.getRoute().getPermission() != null ) {
                if( !Context.get().hasPermission(sr.getRoute().getPermission()) ) {
                    response.sendError(403, "route access denied");
                    return;
                }
            }

            if( sr.getRoute() instanceof GetRoute ) {
                GetRoute getRoute = (GetRoute) sr.getRoute();

                if( getRoute.getFtl() != null ) {
                    String ftl = routing.processVars(getRoute.getFtl(), sr.getVars());
                    processTemplate(ftl, request, response);
                }
                else if( getRoute.getServlet() != null ) {
                    String servlet = routing.processVars(getRoute.getServlet(), sr.getVars());

                    try {
                        Object o = Class.forName(servlet).newInstance();
                        Servlet s = (Servlet) o;
                        s.service(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if( sr.getRoute() instanceof PostRoute ) {
                PostRoute route  = (PostRoute) sr.getRoute();

                String ctrl = routing.processVars(route.getController(), sr.getVars());
                String action = routing.processVars(route.getAction(), sr.getVars());

                // 1. select ctrl
                DispatcherMap.Controller c = DispatcherMap.get().getControllers().get(ctrl);
                if( c == null ) {
                    response.sendError(404, "controller not found");
                    return;
                }

                // 2. select action method
                DispatcherMap.Action a = c.getActions().get(action);
                if( a == null ) {
                    response.sendError(404, "action not found");
                    return;
                }

                // 3. invoke
                ActionResponse ar = ActionInvoker.invokeActionMethod(c.getInstance(), a.getMethod(), request, response);

                // 4. process remappings
                switch(ar.getResponse_type()) {
                    case ActionResponse.DEFAULT:
                        response.sendRedirect("/" + ctrl);
                        break;
                    case ActionResponse.REDIRECT:
                        response.sendRedirect(ar.getKey());
                        break;
                    case ActionResponse.ERROR:
                        response.sendError(ar.getError_code());
                        break;
                    case ActionResponse.JSON:
                        response.getOutputStream().print(ar.getKey());
                        response.getOutputStream().close();
                        break;
                }
            }

        } finally {
            Context.fini();
        }
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
        service((HttpServletRequest)request, (HttpServletResponse) response);
    }

    public void destroy() {
    }
}
