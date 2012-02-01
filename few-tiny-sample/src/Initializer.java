import few.core.ResourceServlet;
import few.routing.RouteBuilder;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 02.02.12
 * Time: 1:38
 * To change this template use File | Settings | File Templates.
 */
public class Initializer implements ServletContextListener{
    public void contextInitialized(ServletContextEvent sce) {
        routes();
    }

    private void routes() {
        RouteBuilder.getRoute("/static/*").toServlet(ResourceServlet.class.getName()).build();
        RouteBuilder.getRoute("/${page}").toPage("/pages/${page}.ftl").build();
        RouteBuilder.getRoute("/").toPage("/pages/.ftl").build();
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
