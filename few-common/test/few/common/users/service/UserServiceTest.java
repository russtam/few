package few.common.users.service;

import few.common.users.persistence.SimpleUser;
import few.utils.Utils;
import junit.framework.TestCase;
import org.junit.BeforeClass;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 01.11.11
 * Time: 20:35
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceTest extends TestCase {
    UserService users;

    @BeforeClass
    protected void setUp() throws Exception {
    }

    public void test1() {
        System.out.println(Utils.produceSHA1fromPassword("admin", "1Q2w3e4r"));
    }

    public String getTestLogin() {
        return "test_user" + Utils.generateUID();
    }

    public String getTestPassword() {
        return "password";
    }

    public SimpleUser getTestUser() {
        Integer id = users.createNewUser("TEST USER", "email", "user", getTestLogin(), getTestPassword(), true);
        return users.selectUser(id);
    }

    public void testSimpleUserCRD() {
        String login = getTestLogin();
        String password = getTestPassword();
        try {
            Integer uid = users.createNewUser("TEST USER", "email", "user", login, password, true);
            assertNotNull(uid);

            uid = users.selectUserBySimpleAuth(login, password).user_id;

            assertNotNull(uid);

            SimpleUser user = users.selectUser(uid);

            assertEquals((int)uid, user.user_id);
            assertEquals("TEST USER", user.display_name);
            assertFalse(user.roles.isEmpty());
            assertTrue(user.roles.contains("user"));

            users.deleteUser(uid);

            SimpleUser u = users.selectUserBySimpleAuth(login, password);
            assertNull(u);

            user = users.selectUser(uid);
            assertNull(user);
        } finally {
            SimpleUser u = users.selectUserByLogin(login);
            if( u != null )
                users.deleteUser(u.user_id);
        }

    }

//    public void testUpdate() {
//        ZingwaSimpleUser newUser = fillNewUser();
//        String login = getTestLogin();
//        String password = getTestPassword();
//        try {
//            zingwa.createUserSimple(newUser, login, password);
//            ZingwaSimpleUser user = zingwa.selectSimpleUser(newUser.id);
//
//            user.image_link = "new link";
//            user.display_name = "new name";
//            zingwa.updateSimpleUser(user);
//
//            ZingwaSimpleUser user2 = zingwa.selectSimpleUser(user.id);
//            assertEquals(user.image_link, user2.image_link);
//            assertEquals(user.display_name, user2.display_name);
//
//            zingwa.updateSimpleUserPassword(user.id, "new password");
//
//            Long uid = zingwa.selectUserBySimpleAuth(login, password);
//            assertNull(uid);
//
//            uid = zingwa.selectUserBySimpleAuth(login, "new password");
//            assertEquals((Long)user.id, uid);
//
//            zingwa.updateSimpleUserPasswordByLogin(login, "new password2");
//
//            uid = zingwa.selectUserBySimpleAuth(login, "new password2");
//            assertNotNull(uid);
//
//        } finally {
//            zingwa.deleteUser(newUser.id);
//        }
//
//    }
//
//    public void testExternalUserCRD() {
//        ZingwaSimpleUser newUser = fillNewUser();
//        String ext_uid = getTestLogin();
//        String code = "test.com";
//
//        Long uid = zingwa.createUserExternal(newUser, ext_uid, code);
//        assertEquals(uid, (Long) newUser.id);
//
//        uid = zingwa.selectUserByExternalID(ext_uid, code);
//
//        assertNotNull(uid);
//        assertEquals((Long)newUser.id, uid);
//
//        zingwa.deleteUser(uid);
//
//        uid = zingwa.selectUserByExternalID(ext_uid, code);
//        assertNull(uid);
//
//        newUser = zingwa.selectSimpleUser(newUser.id);
//        assertNull(newUser);
//
//        zingwa.deletePortal(code);
//
//    }
//
//    public void testCheckLogin() {
//        ZingwaSimpleUser newUser = fillNewUser();
//        String login = getTestLogin();
//        String password = getTestPassword();
//        try {
//            assertTrue(zingwa.getLoginStatus(login));
//
//            zingwa.createUserSimple(newUser, login, password);
//
//            assertFalse(zingwa.getLoginStatus(login));
//
//            zingwa.deleteUser(newUser.id);
//
//            assertTrue(zingwa.getLoginStatus(login));
//        } finally {
//            zingwa.deleteUser(newUser.id);
//        }
//
//    }

}
