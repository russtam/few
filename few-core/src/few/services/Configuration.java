package few.services;


import java.util.Collection;

/**
 * User: gerbylev
 * Date: 31.10.11
 */
public interface Configuration {

    String WEB_SERVER_HOST = "web.server.host";
    String WEB_SERVER_HTTP_PORT =  "web.server.http_port";
    String WEB_SERVER_HTTPS_PORT = "web.server.https_port";
    String AUTOCHECK_INTERVAL = "fremarker.autocheck_interval";


    String getProperty(String key);

    Collection<String> propertyNames();

}
