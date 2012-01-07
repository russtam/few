package few.common;

import few.common.DataConfigProvider;
import few.core.ServiceRegistry;
import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 04.01.12
 * Time: 2:16
 * To change this template use File | Settings | File Templates.
 */
public class BaseTest extends TestCase {

    static {
        ServiceRegistry.registerService(new DBConfig());
    }

    private static class DBConfig implements DataConfigProvider {
        public DataConfigProvider.Conf getConfig(Class clazz) {
            return new Conf("localhost", "5432", "sample", "sample", "sample");
        }
    }

}
