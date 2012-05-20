package few.routing;

import few.Context;
import few.services.Routing;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: igor
 * Date: 20.01.12
 */
public class RoutingImpl implements Routing {

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

    private List<GetRoute> getRoutes = new LinkedList<GetRoute>();
    private List<PostRoute> postRoutes = new LinkedList<PostRoute>();
    private List<ErrorRoute> errorRoutes = new LinkedList<ErrorRoute>();

    public void addGetRoute(GetRoute r) {
        getRoutes.add(r);
    }
    public void addPostRoute(PostRoute r) {
        postRoutes.add(r);
    }
    public void addErrorRoute(ErrorRoute r) {
        errorRoutes.add(r);
    }

    public SelectedRoute selectRoute(HttpServletRequest request) {
        String uri = request.getRequestURI();
        uri = uri.substring(request.getServletContext().getContextPath().length());
        
        Route route = null;;
        Map<String, String> vars = null;
        if( request.getMethod().equals("GET")) {
            for (GetRoute r : getRoutes) {
                vars = processUri(uri, r.getPattern(), r.getVars());
                if( vars != null ) {
                    route = r;
                    break;
                }
            }
        }
        if( request.getMethod().equals("POST")) {
            for (PostRoute r : postRoutes) {
                vars = processUri(uri, r.getPattern(), r.getVars());
                if( vars != null ) {
                    route = r;
                    break;
                }
            }
        }
        if( route != null )
            return new SelectedRoute(route, vars);
        else
            return null;
    }

    public ErrorRoute selectErrorRoute(int code) {
        for (ErrorRoute errorRoute : errorRoutes) {
            if( errorRoute.getCode() == code )
                return errorRoute;
        }
        return null;
    }

    Map<String, String> processUri(String uri, Pattern pattern, String[] vars) {

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

    public String processVars(String pattern, Map<String, String> vars) {
        StringBuilder sb = new StringBuilder();

        int i = 0, j = 0;
        while(true) {
            i = pattern.indexOf("${", j);
            if( i != -1 ) {
                sb.append(pattern.substring(j, i));
                j = pattern.indexOf("}", i);
                String var = pattern.substring(i + 2, j);
                String val = vars.get(var);
                if( val == null )
                    val = Context.get().getRequest().getParameter(var);
                if( val == null )
                    val = (String) Context.get().getRequest().getAttribute(var);
                if( val == null ) {
                    log.severe("no parameter ${" + var + "}");
                }
                else
                    sb.append(val);
                j += 1;
            } else {
                sb.append(pattern.substring(j, pattern.length()));
                break;
            }
        }
        return sb.toString();
    }

    private static Logger log = Logger.getLogger(RoutingImpl.class.getName());
}
