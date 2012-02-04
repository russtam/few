package few.routing;

import few.utils.MapBuilder;
import junit.framework.TestCase;

import java.util.Map;

/**
 * User: igor
 * Date: 20.01.12
 */
public class RouteProcessorTest extends TestCase {

    public void testUriPattern() {
        String uri =     "/users/55/profile";
        String pattern = "/users/${user_id}/profile";

//        Map r = RoutingImpl.processUri(uri, pattern);
//
//        assertNotNull(r);
//        assertTrue(r.containsKey("user_id"));
//        assertEquals("55", r.get("user_id"));

    }

    public void testTemplate() {
        Map<String, String> vars = new MapBuilder<String, String>().
                add("ctrl", "LoginAction").
                add("action", "login");

        String pattern =  "few.common.users.controller.${ctrl}.${action}";
        String expected = "few.common.users.controller.LoginAction.login";

        String r = new RoutingImpl().processVars(pattern, vars);

        assertEquals(expected, r);

    }

}
