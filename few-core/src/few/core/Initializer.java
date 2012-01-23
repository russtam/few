package few.core;

import few.impl.DefaultConfigurationImpl;
import few.impl.DefaultCredentialsImpl;
import few.impl.FreemarkerServiceImpl;
import few.routing.RoutingImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 19.12.11
 * Time: 1:18
 * To change this template use File | Settings | File Templates.
 */
public class Initializer implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServiceRegistry.registerDefaultImpl(DefaultConfigurationImpl.class);
        ServiceRegistry.registerDefaultImpl(DefaultCredentialsImpl.class);

        ServiceRegistry.registerService( new FreemarkerServiceImpl(sce.getServletContext()) );
        ServiceRegistry.registerService( new RoutingImpl() );
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ServiceRegistry.shutdown();
    }
}
