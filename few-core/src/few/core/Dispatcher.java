package few.core;

import few.ActionResponse;
import few.Context;
import few.routing.ErrorRoute;
import few.routing.GetRoute;
import few.routing.PostRoute;
import few.services.FreemarkerService;
import few.services.Routing;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: gerbylev
 * Date: 31.10.11
 */
public class Dispatcher implements Filter{
    public static final String BASE_SERVLET_PATH = "/";

    private ServletContext servletContext;
    private DispatcherMap config;
    private Routing routing;
    private FreemarkerService freemarker;

    private static Dispatcher instance;

    public static Dispatcher get() {
        return instance;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
        freemarker = ServiceRegistry.get(FreemarkerService.class);
        config = DispatcherMap.build(servletContext, Thread.currentThread().getContextClassLoader());
        routing = ServiceRegistry.get(Routing.class);

        instance = this;
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

            FewRequestWrapper  freq  = new FewRequestWrapper(request, sr.getVars());
            FewResponseWrapper fresp = new FewResponseWrapper(request, response);

            Context.init(freq, fresp, servletContext, config);

            if( sr.getRoute().getPermission() != null ) {
                if( !Context.get().hasPermission(sr.getRoute().getPermission()) ) {
                    fresp.sendError(403, "route access denied");
                    return;
                }
            }

            if( sr.getRoute() instanceof GetRoute ) {
                GetRoute getRoute = (GetRoute) sr.getRoute();

                if( getRoute.getFtl() != null ) {
                    String ftl = routing.processVars(getRoute.getFtl(), sr.getVars());
                    if( freemarker.checkExists(ftl) )
                        processTemplate(ftl, request, response);
                    else {
                        fresp.sendError(404, "template not found");
                        return;
                    }
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
                    fresp.sendError(404, "controller not found");
                    return;
                }

                // 2. select action method
                DispatcherMap.Action a = c.getActions().get(action);
                if( a == null ) {
                    fresp.sendError(404, "action not found");
                    return;
                }

                // 3. invoke
                ActionResponse ar = ActionInvoker.invokeActionMethod(c.getInstance(), a.getMethod(), request, response);

                // 4. process remappings
                String where ;
                switch(ar.getResponse_type()) {
                    case ActionResponse.DEFAULT:
                        where = route.getRemapping().get("default");
                        if( where != null ) {
                            where = routing.processVars(where, sr.getVars());
                            response.sendRedirect(where);
                        } else
                            response.sendRedirect("/" + ctrl);
                        break;
                    case ActionResponse.PAGE:
                        where = route.getRemapping().get(ar.getKey());
                        if( where != null ) {
                            where = routing.processVars(where, sr.getVars());
                            response.sendRedirect(where);
                        } else
                            response.sendRedirect("/" + ar.getKey());
                        break;
                    case ActionResponse.REDIRECT:
                        response.sendRedirect(ar.getKey());
                        break;
                    case ActionResponse.ERROR:
                        response.sendError(ar.getError_code());
                        break;
                    case ActionResponse.JSON:
		        response.setContentType("application/json");
                        response.getOutputStream().print(ar.getKey());
                        response.getOutputStream().close();
                        break;
                }
            }

        } finally {
            try {
                Context.fini();
            } catch(Exception e) {
                log.log(Level.SEVERE, "", e);
            }
        }
    }

    public void processError(int sc, String msg, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorRoute er = routing.selectErrorRoute(sc);
        if( er == null ) {
            response.sendError(sc, msg);
            return;
        }

        if( er.getFtl() != null ) {
            String ftl = routing.processVars(er.getFtl(), Collections.<String, String>emptyMap());
            if( freemarker.checkExists(ftl) )
                processTemplate(ftl, request, response);
            else {
                response.sendError(sc, msg + "  \t\nerror template " + ftl + " not found");
                return;
            }
        }
        if( er.getServlet() != null ) {
            String servlet = routing.processVars(er.getServlet(), Collections.<String, String>emptyMap());

            try {
                Object o = Class.forName(servlet).newInstance();
                Servlet s = (Servlet) o;
                s.service(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void processTemplate(String template, HttpServletRequest request, HttpServletResponse response) throws IOException{
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

    Logger log = Logger.getLogger(Dispatcher.class.getName());
}
