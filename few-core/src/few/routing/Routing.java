package few.routing;

import few.ActionResponse;
import few.Context;
import few.core.ActionInvoker;
import few.core.DispatcherMap;
import few.core.ServiceRegistry;
import few.services.Credentials;
import few.services.FreemarkerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 20.01.12
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public class Routing {

    /*
    идея такова:
     так как мы используем PRG, то мы можем чётко разделять запросы POST/GET
     AJAX запросы пока отдельно не отделяем (а можно было бы)
    1. производится выборка правил по Методу запроса
    2. у каждого правила есть urlPatern - regexp по которому сопоставляются запросы/правила
    3. urlPatern может содержать параметры в виде /users/${id}/profile, эти параметры подставяться в параметры HttpRequest'а
    4. требуемое разрешение
    5. если это GET запрос, то у негно могут быть следующие правила
        5.1 ftl-страница которой он соответствует
        5.2 мапинг на сервлет.
    6. если это POST запрос, то у него могут быть следующие правила
        6.1 контроллер и action-метод, соответствующий запросу. возможны маски.
            контроллер может указываться по default_name, если оно уникально.
        6.2 ремаппинг ответов контроллера (если тот возвращает коды)

    Разрешения могут быть заданы и будут проверяться у:
     ModelBeans, Классы и методы контроллеров, urlPattern'ы в роутинге.

     Модель маршрутов задана в классах GetRoute и PostRoute.
     Для их создания используется RouteBuilder

     ремаппинг model-beans и их параметров.
     */

    {
        // default router configuration
        RouteBuilder.getRoute("/").toServlet("defaultPageServlet").build();
        RouteBuilder.getRoute("/static/*").toServlet("resourceServlet").build();

        RouteBuilder.getRoute("/${page}").toPage("/pages/${page}.ftl").build();

        // render method - redirections ???
        // примеры - login - редирек на корень, если залогинен - нахх
        // всякие счётчики - GET'ами...
        // push-запросы - всё сервлетами.
        // with controller ?

        RouteBuilder.postRoute("/${ctrl}").ctrl("${ctrl}").action("");
        //RouteBuilder.postRoute("/${ctrl}.${action}").ctrl("${ctrl}").action("${action}");

    }

    Credentials credentials = ServiceRegistry.get(Credentials.class);
    public boolean processGetRoute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uri = request.getRequestURI();
        GetRoute route = null;
        Map<String, String> vars = null;
        for (GetRoute r : RouteBuilder.getRoutes) {
            vars = RouteProcessor.processUri(uri, r.getPattern(), r.getVars());
            if( vars != null ) {
                route = r;
            }
        }
        if( route == null ) {
            response.sendError(404, "route not found");
            return false;
        }

        if( route.getPermission() != null ) {
            if( !Context.get().isUserInRole(route.getPermission()) ) {
                response.sendError(403);
            }
        }

        if( route.getFtl() != null ) {
            String ftl = RouteProcessor.processVars( route.getFtl(), vars );
            processTemplate(ftl, request, response);
        }
        else if( route.getServlet() != null ) {
            String servlet = RouteProcessor.processVars( route.getFtl(), vars );
            request.getRequestDispatcher( servlet ).
                    forward(request, response);
        }

        return true;
    }

    public boolean processPostRoute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uri = request.getRequestURI();
        PostRoute route = null;
        Map<String, String> vars = null;
        for (PostRoute r : RouteBuilder.postRoutes) {
            vars = RouteProcessor.processUri(uri, r.getPattern(), r.getVars());
            if( vars != null ) {
                route = r;
            }
        }
        if( route == null ) {
            response.sendError(404, "route not found");
            return false;
        }

        if( route.getPermission() != null ) {
            if( !Context.get().isUserInRole(route.getPermission()) ) {
                response.sendError(403, "route denied");
            }
        }

        String ctrl = RouteProcessor.processVars( route.getController(), vars );
        String action = RouteProcessor.processVars( route.getAction(), vars );

        // 1. select ctrl
        DispatcherMap.Controller c = DispatcherMap.get().getControllers().get(ctrl);
        if( c == null ) {
            response.sendError(404, "controller not found");
            return false;
        }

        // 2. select action method
        DispatcherMap.Action a = c.getActions().get(action);
        if( a == null ) {
            response.sendError(404, "action not found");
            return false;
        }

        // 3. invoke
        ActionResponse ar = ActionInvoker.invokeActionMethod(c.getInstance(), a.getMethod(), request, response);

        // 4. process remappings
        switch(ar.getResponse_type()) {
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


        return true;
    }

    FreemarkerService freemarker = ServiceRegistry.get(FreemarkerService.class);
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

}
