package few.common;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 23.08.11
 * Time: 12:22
 * To change this template use File | Settings | File Templates.
 */
public interface DataConfigProvider {

    public static class Conf {
        public final String ip;
        public final String port;
        public final String dbname;
        public final String user;
        public final String password;

        public Conf(String ip, String port, String dbname, String user, String password) {
            this.ip = ip;
            this.port = port;
            this.dbname = dbname;
            this.user = user;
            this.password = password;
        }
    }

    Conf getConfig(Class clazz);
}
