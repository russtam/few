package few.routing;

import few.core.ResourceServlet;

/**
 * User: igor
 * Date: 22.01.12
 */
public class DefaultRoutes {

    public static void apply() {
        // default router configuration
        //RouteBuilder.getRoute("/").toServlet(ResourceServlet.class.getName()).build();
        RouteBuilder.getRoute("/static/*").toServlet(ResourceServlet.class.getName()).build();
        RouteBuilder.getRoute("/robots.txt").toServlet(ResourceServlet.class.getName()).build();
        RouteBuilder.getRoute("/favicon.ico").toServlet(ResourceServlet.class.getName()).build();

        RouteBuilder.getRoute("/${page}").toPage("/pages/${page}.ftl").build();

        // render method - redirections ???
        // примеры - login - редирек на корень, если залогинен - нахх
        // всякие счётчики - GET'ами...
        // push-запросы - всё сервлетами.
        // with controller ?

        //RouteBuilder.postRoute("/${ctrl}").ctrl("${ctrl}").action("");
        RouteBuilder.postRoute("/${ctrl}.${action}").ctrl("${ctrl}").action("${action}").build();
    }

}
