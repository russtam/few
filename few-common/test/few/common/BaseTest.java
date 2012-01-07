package few.common;

import few.common.DataConfigProvider;
import few.core.ServiceRegistry;
import few.services.Credentials;
import junit.framework.TestCase;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

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

        ServiceRegistry.registerService(new Credentials() {
            public String getUserID(HttpServletRequest request) {
                return "1";
            }

            public boolean isSignedIn(HttpServletRequest request) {
                return true;
            }

            public Set<String> getRoles(HttpServletRequest request) {
                return null;
            }
        });
    }



    @Override
    public void setUp() throws Exception {
        TestContext.set();
    }

    private static class DBConfig implements DataConfigProvider {
        public DataConfigProvider.Conf getConfig(Class clazz) {
            return new Conf("localhost", "5432", "sample", "sample", "sample");
        }
    }

}
