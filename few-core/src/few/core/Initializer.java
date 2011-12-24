package few.core;

import few.impl.DefaultConfigurationImpl;
import few.impl.DefaultCredentialsImpl;
import few.impl.FreemarkerServiceImpl;
import few.services.Configuration;

import javax.servlet.ServletContext;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 19.12.11
 * Time: 1:18
 * To change this template use File | Settings | File Templates.
 */
public class Initializer {

    public static void init(ServletContext context) {
        ServiceRegistry.registerDefaultImpl(DefaultConfigurationImpl.class);
        ServiceRegistry.registerDefaultImpl(DefaultCredentialsImpl.class);

        ServiceRegistry.registerService( new FreemarkerServiceImpl(context) );

    }

    public static void fini() {
        ServiceRegistry.shutdown();
    }

}
