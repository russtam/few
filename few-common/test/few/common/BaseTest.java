package few.common;

import few.annotations.AnnotationFinderImpl;
import few.common.DataConfigProvider;
import few.core.ServiceRegistry;
import few.services.AnnotationFinder;
import few.services.Credentials;
import few.utils.ListBuilder;
import junit.framework.TestCase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
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

            public String getUserID(HttpSession session) {
                return "1";
            }

            public boolean isSignedIn(HttpServletRequest request) {
                return true;
            }

            public Set<String> getRoles(HttpServletRequest request) {
                return null;
            }
        });

        ServiceRegistry.registerService(new AnnotationFinder() {

            public Map<Class, List<Class>> findAnnotations() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public List<String> findXmlFiles() {
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                return new ListBuilder<String>()
                        .build(cl.getResource("few/common/audit/dao/Audit.xml").getFile())
                        .build(cl.getResource("few/common/cms/dao/CMS.xml").getFile())
                        .build(cl.getResource("few/common/users/dao/Users.xml").getFile())
                        .build(cl.getResource("few/common/users/dao/Confirmations.xml").getFile());
            }
        }
        );
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
