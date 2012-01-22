package few.sample;

import few.common.audit.service.AuditKeys;
import few.common.audit.service.AuditService;
import few.common.users.service.CredentialsImpl;
import few.core.ServiceRegistry;
import few.impl.DefaultConfigurationImpl;
import few.routing.DefaultRoutes;
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
        DefaultRoutes.apply();
        RouteBuilder.getRoute("/").toServlet(DefaultPage.class.getName()).build();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
