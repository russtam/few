package few.sample;

import few.common.DataConfigProvider;
import few.core.ServiceRegistry;
import few.services.Configuration;

/**
 * User: igor
 * Date: 19.12.11
 */
public class DBConfig implements DataConfigProvider{

    Configuration cfg = ServiceRegistry.get(Configuration.class);
    Conf conf = new Conf(
            cfg.getProperty("sample_db.ip"),
            cfg.getProperty("sample_db.port"),
            cfg.getProperty("sample_db.dbname"),
            cfg.getProperty("sample_db.user"),
            cfg.getProperty("sample_db.password")
    );

    public Conf getConfig(Class clazz) {
        return conf;
    }

}
