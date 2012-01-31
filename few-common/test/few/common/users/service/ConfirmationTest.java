package few.common.users.service;

import few.common.BaseTest;
import few.common.users.dao.ConfirmationService;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 04.11.11
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */

public class ConfirmationTest extends BaseTest {
    ConfirmationService sa = ConfirmationService.get();


    public void testCD() {

        String key = sa.createConfirmationKey(new String[]{"p1"}, ConfirmationService.ONE_HOUR_TIMEOUT*2);
        assertNotNull(key);

        String params[] = sa.useConfirmationKey(key);
        assertNotNull(params);
        assertEquals(1, params.length);
        assertEquals("p1", params[0]);

    }

    public void testCD2() {

        String key = sa.createConfirmationKey(new String[]{"login", "password"}, ConfirmationService.ONE_HOUR_TIMEOUT*2);
        assertNotNull(key);

        String params[] = sa.useConfirmationKey(key);
        assertNotNull(params);
        assertEquals(2, params.length);
        assertEquals("login", params[0]);
        assertEquals("password", params[1]);

    }

}
