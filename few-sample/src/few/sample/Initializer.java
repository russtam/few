package few.sample;

import few.common.users.service.CredentialsImpl;
import few.core.ResourceServlet;
import few.core.ServiceRegistry;
import few.impl.DefaultConfigurationImpl;
import few.routing.RouteBuilder;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 19.12.11
 * Time: 2:10
 * To change this template use File | Settings | File Templates.
 */
public class Initializer implements ServletContextListener{
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServiceRegistry.registerService(new DefaultConfigurationImpl("few-sample.properties"));
        ServiceRegistry.registerService(DBConfig.class);
        ServiceRegistry.registerService(CredentialsImpl.class);
        ServiceRegistry.registerService(CustomUserProfile.class);

        // fill routes
        routes();
    }

    public void routes() {
        //RouteBuilder.getRoute("/").toServlet(ResourceServlet.class.getName()).build();
        RouteBuilder.getRoute("/robots.txt").toServlet(ResourceServlet.class.getName()).build();
        RouteBuilder.getRoute("/favicon.ico").toServlet(ResourceServlet.class.getName()).build();
        RouteBuilder.getRoute("/static/*").toServlet(ResourceServlet.class.getName()).build();

        // RouteBuilder.permission("/admin*", "admin").build();
        // RouteBuilder.getRoute("/admin/users").withParam("actionId", "55")

        RouteBuilder.getRoute("/admin/${page}").toPage("/pages/admin/${page}.ftl").permission("admin").build();
        RouteBuilder.getRoute("/user/${page}").toPage("/pages/user/${page}.ftl").permission("user").build();

        RouteBuilder.getRoute("/${page}").toPage("/pages/${page}.ftl").build();

        // render method - redirections ???
        // примеры - login - редирек на корень, если залогинен - нахх
        // всякие счётчики - GET'ами...
        // push-запросы - всё сервлетами.
        // with controller ?

        //RouteBuilder.postRoute("/${ctrl}").ctrl("${ctrl}").action("");
        RouteBuilder.postRoute("/user_list.delete").ctrl("user_list").action("delete").
                map("default", "/admin/user_list").build();
        RouteBuilder.postRoute("/user_list.${action}").ctrl("user_list").action("${action}").
                map("default", "/admin/user_edit?user_id=${user_id}").build();

        RouteBuilder.postRoute("/${ctrl}.${action}").ctrl("${ctrl}").action("${action}").build();

        RouteBuilder.getRoute("/").toServlet(DefaultPage.class.getName()).build();

        RouteBuilder.errorRoute(404).toPage("/pages/_errors/404.ftl").build();
        RouteBuilder.errorRoute(403).toServlet(AccessDeniedPage.class.getName()).build();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
