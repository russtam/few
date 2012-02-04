package few.core;

import few.annotations.AnnotationFinderImpl;
import few.impl.DefaultConfigurationImpl;
import few.impl.DefaultCredentialsImpl;
import few.impl.FreemarkerServiceImpl;
import few.routing.RoutingImpl;
import few.services.AnnotationFinder;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * User: igor
 * Date: 19.12.11
 */
public class Initializer implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServiceRegistry.registerService(new AnnotationFinderImpl(sce.getServletContext(), Thread.currentThread().getContextClassLoader()));
        ServiceRegistry.registerDefaultImpl(DefaultConfigurationImpl.class);
        ServiceRegistry.registerDefaultImpl(DefaultCredentialsImpl.class);

        ServiceRegistry.registerService( new FreemarkerServiceImpl(sce.getServletContext()) );
        ServiceRegistry.registerService( new RoutingImpl() );
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ServiceRegistry.shutdown();
    }
}
