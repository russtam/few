package few.sample;

import few.common.DataConfigProvider;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 19.12.11
 * Time: 2:08
 * To change this template use File | Settings | File Templates.
 */
public class DBConfig implements DataConfigProvider{

    Conf conf = new Conf(
            "localhost", "5432", "drgn", "drgn", "drgn"
    );

    public Conf getConfig(Class clazz) {
        return conf;
    }

}
