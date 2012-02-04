package few.services;

import few.routing.ErrorRoute;
import few.routing.GetRoute;
import few.routing.PostRoute;
import few.routing.Route;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * User: igor
 * Date: 23.01.12
 */
public interface Routing {
    void addGetRoute(GetRoute r);

    void addPostRoute(PostRoute r);

    void addErrorRoute(ErrorRoute r);


    public static class SelectedRoute {
        private Route route;
        private Map<String, String> vars;

        public SelectedRoute(Route route, Map<String, String> vars) {
            this.route = route;
            this.vars = vars;
        }

        public Route getRoute() {
            return route;
        }

        public Map<String, String> getVars() {
            return vars;
        }
    }

    SelectedRoute selectRoute(HttpServletRequest request);

    ErrorRoute selectErrorRoute(int code);

    String processVars(String pattern, Map<String, String> vars);
}
