package few.common.users.service;

import few.common.DataConfigProvider;
import few.common.users.persistence.SimpleUser;
import few.core.ServiceRegistry;
import few.utils.Utils;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 01.11.11
 * Time: 20:35
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceTest extends TestCase {

    private List<Integer> createdUsersId = new ArrayList<Integer>();
    private UserService users;

    final String displayName = "displayName";
    final String email = "my.name@some.org";
    final String role = "user";
    final String login = "my.login";
    final String pwd = "pwd";
    final boolean active = true;

    static {
        ServiceRegistry.registerService(new DBConfig());
    }

    protected void setUp() throws Exception {
        users = UserService.get();
    }

    public void tearDown() throws Exception {
        for (Integer userId : createdUsersId) {
            users.deleteUser(userId);
        }
    }

    public void test1() {
        System.out.println(Utils.produceSHA1fromPassword("admin", "admin"));
    }

    public String getTestLogin() {
        return "test_user" + Utils.generateUID();
    }

    public String getTestPassword() {
        return "password";
    }

    public void test_created_user_has_fields_passed_to_create_method() {
        Integer userId = createUser(displayName, email, role, login, pwd, active);
        SimpleUser user = users.selectUser(userId);
        assertEquals(displayName, user.getDisplay_name());
        assertEquals(email, user.getEmail());
        assertTrue(user.getRoles().contains(role));
        assertEquals(active ? 1 : 0, user.getStatus_id());
    }

    private Integer createUser(String displayName, String email, String role, String login, String pwd, boolean active) {
        Integer userId = users.createNewUser(displayName, email, role, login, pwd, active);
        createdUsersId.add(userId);
        return userId;
    }

    public void test_deleted_user_cannot_be_selected() {
        Integer userId = createUser(displayName, email, role, login, pwd, active);
        users.deleteUser(userId);
        assertNull(users.selectUser(userId));
    }

    public void test_user_with_updated_login_has_same_fields() {
        Integer userId = createUser(displayName, email, role, login, pwd, active);
        users.updateLogin(userId, "new_login");
        SimpleUser user_selected_by_login = users.selectUserByLogin("new_login");
        SimpleUser user_selected_by_id = users.selectUser(userId);
        assertEquals(user_selected_by_login, user_selected_by_id);
    }

    public void test_user_with_updated_password_has_same_fields() {
        Integer userId = createUser(displayName, email, role, login, pwd, active);
        users.updateUserPassword(login, "new_pwd");
        SimpleUser user = users.selectUser(userId);
        SimpleUser same_user_with_new_pwd = users.selectUserBySimpleAuth(login, "new_pwd");
        assertEquals(user, same_user_with_new_pwd);
    }

    public void test_activated_user_has_active_status() {
        Integer userId = createUser(displayName, email, role, login, pwd, false);
        users.activateUser(userId);
        SimpleUser user = users.selectUser(userId);
        assertEquals(SimpleUser.ACTIVE, user.getStatus_id());
    }

    public void test_select_user_by_simple_authorization() {
        Integer userId = createUser(displayName, email, role, login, pwd, active);
        SimpleUser user_selected_by_id = users.selectUser(userId);
        SimpleUser user_selected_by_simple_auth = users.selectUserBySimpleAuth(login, pwd);
        assertEquals(user_selected_by_id, user_selected_by_simple_auth);
    }

    public void test_select_user_by_login() {
        Integer userId = createUser(displayName, email, role, login, pwd, active);
        SimpleUser user_selected_by_id = users.selectUser(userId);
        SimpleUser user_selected_by_login = users.selectUserByLogin(login);
        assertEquals(user_selected_by_id , user_selected_by_login);
    }

    public void test_select_user_by_email() {
        Integer userId = createUser(displayName, email, role, login, pwd, active);
        SimpleUser user_selected_by_id = users.selectUser(userId);
        SimpleUser user_selected_by_email = users.selectUserByEMail(email);
        assertEquals(user_selected_by_id, user_selected_by_email);
    }

    public void test_select_users() {
        Integer userId = createUser(displayName, email, role, login, pwd, active);
        SimpleUser user = users.selectUser(userId);
        List<SimpleUser> simpleUsersList = users.selectUsers();
        assertTrue(simpleUsersList.contains(user));
    }

    public void test_select_users_resets_cache() {
        int number_of_users_before_insert = users.selectUsers().size();
        createUser(displayName, email, role, login, pwd, active);
        int number_of_users_after_insert = users.selectUsers().size();
        assertEquals(number_of_users_before_insert + 1, number_of_users_after_insert);
    }

    public void test_update_simple_user() {
        Integer userId = createUser(displayName, email, role, login, pwd, active);
        SimpleUser user = users.selectUser(userId);
        user.display_name = "new_name";
        user.email = "new_email";
        users.updateSimpleUser(user);
        SimpleUser user_after_update = users.selectUser(userId);
        assertEquals("new_name", user_after_update.getDisplay_name());
        assertEquals("new_email", user_after_update.getEmail());
    }

    public void test_select_login_by_user_id() {
        Integer userId = createUser(displayName, email, role, login, pwd, active);
        SimpleUser user = users.selectUser(userId);
        SimpleUser user_selected_by_login = users.selectUserByLogin(users.selectLoginByUserID(userId));
        assertEquals(user, user_selected_by_login);
    }

    public void test_select_login_by_email() {
        Integer userId = createUser(displayName, email, role, login, pwd, active);
        SimpleUser user = users.selectUser(userId);
        SimpleUser user_selected_by_login = users.selectUserByLogin(users.selectLoginByEMail(email));
        assertEquals(user, user_selected_by_login);
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

  private static class DBConfig implements DataConfigProvider {
    public DataConfigProvider.Conf getConfig(Class clazz) {
      return new Conf("localhost", "5432", "sample", "postgres", "password123");
    }
  }

}
