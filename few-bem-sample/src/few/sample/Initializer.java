package few.sample;

import few.common.users.service.CredentialsImpl;
import few.core.ResourceServlet;
import few.core.ServiceRegistry;
import few.impl.DefaultConfigurationImpl;
import few.routing.RouteBuilder;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * User: igor
 * Date: 19.12.11
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
        RouteBuilder.getRoute("/blocks/*.css").toServlet(ResourceServlet.class.getName()).build();

        // RouteBuilder.permission("/admin*", "admin").build();
        // RouteBuilder.getRoute("/admin/users").withParam("actionId", "55")

        RouteBuilder.getRoute("/admin/${page}").toPage("/bem_pages/admin/p_${page}.ftl").permission("admin").build();
        RouteBuilder.getRoute("/user/${page}").toPage("/bem_pages/user/p_${page}.ftl").permission("user").build();

        RouteBuilder.getRoute("/${page}").toPage("/bem_pages/p_${page}.ftl").build();

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
        RouteBuilder.postRoute("/user_profile.${action}").ctrl("user_profile").action("${action}").
                map("default", "/user/profile").build();

        RouteBuilder.postRoute("/${ctrl}.${action}").ctrl("${ctrl}").action("${action}").build();

        RouteBuilder.getRoute("/").toServlet(DefaultPage.class.getName()).build();

        RouteBuilder.errorRoute(404).toPage("/bem_pages/_errors/p_404.ftl").build();
        RouteBuilder.errorRoute(403).toServlet(AccessDeniedPage.class.getName()).build();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
