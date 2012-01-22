package few.routing;

import few.ActionResponse;
import few.Context;
import few.core.ActionInvoker;
import few.core.DispatcherMap;
import few.core.ResourceServlet;
import few.core.ServiceRegistry;
import few.services.Credentials;
import few.services.FreemarkerService;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 20.01.12
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public class RouteProcessor {

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

    Credentials credentials = ServiceRegistry.get(Credentials.class);
    public boolean processGetRoute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uri = request.getRequestURI();
        GetRoute route = null;
        Map<String, String> vars = null;
        for (GetRoute r : RouteBuilder.getRoutes) {
            vars = processUri(uri, r.getPattern(), r.getVars());
            if( vars != null ) {
                route = r;
                break;
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
            String ftl = processVars(route.getFtl(), vars);
            processTemplate(ftl, request, response);
        }
        else if( route.getServlet() != null ) {
            String servlet = processVars(route.getServlet(), vars);

            try {
                Object o = Class.forName(servlet).newInstance();
                Servlet s = (Servlet) o;
                s.service(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public boolean processPostRoute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uri = request.getRequestURI();
        PostRoute route = null;
        Map<String, String> vars = null;
        for (PostRoute r : RouteBuilder.postRoutes) {
            vars = processUri(uri, r.getPattern(), r.getVars());
            if( vars != null ) {
                route = r;
                break;
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

        String ctrl = processVars(route.getController(), vars);
        String action = processVars(route.getAction(), vars);

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


    static class PatternWithVars {
        private Pattern pattern;
        private String[] vars;

        public PatternWithVars(Pattern pattern, String[] vars) {
            this.pattern = pattern;
            this.vars = vars;
        }

        public Pattern getPattern() {
            return pattern;
        }

        public String[] getVars() {
            return vars;
        }
    }

    static PatternWithVars buildPattern(String pattern) {
        ArrayList<String> vars = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        sb.append("^");

        pattern = pattern.replace(".", "\\.");
        pattern = pattern.replace("*", ".*");

        int i = 0, j = 0;
        while(true) {
            i = pattern.indexOf("${", j);
            if( i != -1 ) {
                sb.append(pattern.substring(j, i));
                sb.append("(.+)");

                j = pattern.indexOf("}", i);
                String var = pattern.substring(i + 2, j);
                vars.add(var);
                j += 1;
            } else {
                sb.append(pattern.substring(j, pattern.length()));
                break;
            }
        }
        //sb.append(".*");

        Pattern pt = Pattern.compile(sb.toString());

        return new PatternWithVars(pt, vars.toArray(new String[vars.size()]));
    }

    static Map<String, String> processUri(String uri, String pattern) {
        PatternWithVars pv = buildPattern(pattern);

        return processUri(uri, pv.getPattern(), pv.getVars());
    }

    static Map<String, String> processUri(String uri, Pattern pattern, String[] vars) {

        Matcher m = pattern.matcher(uri);
        if( m.matches() ) {
            MatchResult mr = m.toMatchResult();

            Map<String, String> map = new HashMap<String, String>();
            for (int k = 0; k < vars.length; k++)
                map.put(vars[k], mr.group(k+1));

            return Collections.unmodifiableMap(map);
        }
        else
            return null;
    }

    static String processVars(String pattern, Map<String, String> vars) {
        StringBuilder sb = new StringBuilder();

        int i = 0, j = 0;
        while(true) {
            i = pattern.indexOf("${", j);
            if( i != -1 ) {
                sb.append(pattern.substring(j, i));
                j = pattern.indexOf("}", i);
                String var = pattern.substring(i + 2, j);
                String val = vars.get(var);
                if( val == null ) {
                    log.severe("no parameter ${" + var + "}");
                }
                else
                    sb.append(vars.get(var));
                j += 1;
            } else {
                sb.append(pattern.substring(j, pattern.length()));
                break;
            }
        }
        return sb.toString();
    }

    static Logger log = Logger.getLogger(RouteProcessor.class.getName());
}
