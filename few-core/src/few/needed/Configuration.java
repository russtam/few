package few.needed;


/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 31.10.11
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */
public interface Configuration {

    String WEB_SERVER_HOST = "web.server.host";
    String WEB_SERVER_HTTP_PORT =  "web.server.http_port";
    String WEB_SERVER_HTTPS_PORT = "web.server.https_port";
    String AUTOCHECK_INTERVAL = "fremarker.autocheck_interval";


    String getString(String key);

    int getInteger(String key);

    boolean getBoolean(String key);

}
