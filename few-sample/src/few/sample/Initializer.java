package few.sample;

import few.common.users.service.CredentialsImpl;
import few.core.ServiceRegistry;
import few.impl.DefaultConfigurationImpl;

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
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
