package few.embedded;

import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.startup.Embedded;

import java.io.File;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 18.12.11
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
 */
public class Alone {
    private Embedded container = null;

    public Alone() {
    }

    /**
     * Starts the embedded Tomcat server.
     *
     * @throws LifecycleException
     * @throws MalformedURLException if the server could not be configured
     * @throws LifecycleException if the server could not be started
     * @throws MalformedURLException
     */
    public void run(int port, int portSecure, String webappEplodedDir, String web_path, String catalina_home) throws LifecycleException, MalformedURLException {
        /**
         * The classes directory for the web application being run.
         */

        // create server
        container = new Embedded();
        container.setCatalinaHome(catalina_home);

        // create webapp loader
        WebappLoader loader = new WebappLoader(this.getClass().getClassLoader());

        // create context
        Context context = container.createContext(web_path, webappEplodedDir);

        context.setLoader(loader);
        context.setReloadable(true);

        // create host
        // String appBase = new File(catalinaHome, "webapps").getAbsolutePath();
        Host localHost = container.createHost("localHost", new File("").getAbsolutePath());
        localHost.addChild(context);

        // create engine
        Engine engine = container.createEngine();
        engine.setName("localEngine");
        engine.addChild(localHost);
        engine.setDefaultHost(localHost.getName());
        container.addEngine(engine);

        // create http connector
        Connector httpConnector = container.createConnector((InetAddress) null, port, false);
        container.addConnector(httpConnector);

        Connector httpsConnector = container.createConnector((InetAddress) null, portSecure, true);
        httpsConnector.setScheme("https");
        httpsConnector.setProperty("keystoreFile",
                this.getClass().getClassLoader().getResource("ssl_key").getFile());
        httpsConnector.setProperty("keystorePass", "tomcat");
        httpsConnector.setProperty("keystoreType", "JKS");

        container.addConnector(httpsConnector);

        container.setAwait(true);

        // start server
        container.start();

        // add shutdown hook to stop server
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                stopContainer();
            }
        });
    }

    /**
     * Stops the embedded Tomcat server.
     */
    public void stopContainer() {
        try {
            if (container != null) {
                container.stop();
            }
        } catch (LifecycleException exception) {
            System.err.println("Cannot Stop Tomcat" + exception.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        String web_home      = System.getProperty("webapp.home");
        String catalina_home = System.getProperty("catalina.home", "tomcat-embedded");
        String web_path      = System.getProperty("webapp.path", "");
        String port_http     = System.getProperty("port.http", "9280");
        String port_https    = System.getProperty("port.https", "9281");

        if(web_home == null) {
            System.out.println("Usage: ");
            System.out.println("   -Dwebapp.home=<path to exploded webapp>              required");
            System.out.println("   -Dwebapp.path=[context path for webapp]              default - ''");
            System.out.println("   -Dcatalina.home=[path to working folder of tomcat]   default - tomcat-embedded");
            System.out.println("   -Dport.http=[port for http connector]                default - 9280");
            System.out.println("   -Dport.http=[port for https connector]               default - 9281");
            return;
        }

        long time = System.currentTimeMillis();
        new Alone().run(Integer.valueOf(port_http), Integer.valueOf(port_https), web_home, web_path, catalina_home);
        int sec = (int) ((System.currentTimeMillis() - time)/1000);
        System.out.println("started in " + sec + " sec");

        Thread.sleep(Integer.MAX_VALUE);
    }

}