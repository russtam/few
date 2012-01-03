package few.common.users.service;

import com.sun.org.apache.xpath.internal.operations.Equals;
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

    private final String displayName = "displayName";
    private final String email = "my.name@some.org";
    private final String role = "user";
    private final String login = "my.login";
    private final String pwd = "pwd";
    private final boolean active = true;

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
        SimpleUser user = createAndSelectUser();
        assertEquals(displayName, user.getDisplay_name());
        assertEquals(email, user.getEmail());
        assertTrue(user.getRoles().contains(role));
        assertEquals(active ? 1 : 0, user.getStatus_id());
    }

    public void test_deleted_user_cannot_be_selected() {
        SimpleUser user = createAndSelectUser();
        users.deleteUser(user.user_id);
        assertNull(users.selectUser(user.user_id));
    }

    public void test_user_with_updated_login_has_same_fields() {
        Integer userId = createUser(displayName, email, role, login, pwd, active);
        users.updateLogin(userId, "new_login");
        SimpleUser user_selected_by_login = users.selectUserByLogin("new_login");
        SimpleUser user_selected_by_id = users.selectUser(userId);
        assertTrue(users_has_same_fields(user_selected_by_login, user_selected_by_id));
    }

    public void test_user_with_updated_password_has_same_fields() {
        Integer userId = createUser(displayName, email, role, login, pwd, active);
        users.updateUserPassword(login, "new_pwd");
        SimpleUser user = users.selectUser(userId);
        SimpleUser same_user_with_new_pwd = users.selectUserBySimpleAuth(login, "new_pwd");
        assertTrue(users_has_same_fields(user, same_user_with_new_pwd));
    }

    public void test_activated_user_has_active_status() {
        Integer userId = createUser(displayName, email, role, login, pwd, false);
        users.activateUser(userId);
        SimpleUser user = users.selectUser(userId);
        assertEquals(SimpleUser.ACTIVE, user.getStatus_id());
    }

    public void test_select_user_by_simple_authorization() {
        SimpleUser user_selected_by_id = createAndSelectUser();
        SimpleUser user_selected_by_simple_auth = users.selectUserBySimpleAuth(login, pwd);
        assertTrue(users_has_same_fields(user_selected_by_id, user_selected_by_simple_auth));
    }

    public void test_select_user_by_login() {
        SimpleUser user_selected_by_id = createAndSelectUser();
        SimpleUser user_selected_by_login = users.selectUserByLogin(login);
        assertTrue(users_has_same_fields(user_selected_by_id, user_selected_by_login));
    }

    public void test_select_user_by_email() {
        SimpleUser user_selected_by_id = createAndSelectUser();
        SimpleUser user_selected_by_email = users.selectUserByEMail(email);
        assertTrue(users_has_same_fields(user_selected_by_id, user_selected_by_email));
    }

    public void test_select_users() {
        SimpleUser user = createAndSelectUser();
        List<SimpleUser> simpleUsersList = users.selectUsers();
        assertTrue(simpleUsersList.contains(user));
    }

    public void test_select_users_resets_cache() {
        int number_of_users_before_insert = users.selectUsers().size();
        createUser(displayName, email, role, login, pwd, active);
        int number_of_users_after_insert = users.selectUsers().size();
        assertEquals(number_of_users_before_insert + 1, number_of_users_after_insert);
    }

    public void test_select_users_by_role() {
        SimpleUser user = createAndSelectUser();
        assertTrue(users.selectUsersByRole(role).contains(user));
    }

    public void test_update_simple_user() {
        SimpleUser user = createAndSelectUser();
        user.display_name = "new_name";
        user.email = "new_email";
        users.updateSimpleUser(user);
        SimpleUser user_after_update = users.selectUser(user.user_id);
        assertEquals("new_name", user_after_update.getDisplay_name());
        assertEquals("new_email", user_after_update.getEmail());
    }

    public void test_select_login_by_user_id() {
        SimpleUser user = createAndSelectUser();
        SimpleUser user_selected_by_login = users.selectUserByLogin(users.selectLoginByUserID(user.user_id));
        assertTrue(users_has_same_fields(user, user_selected_by_login));
    }

    public void test_select_login_by_email() {
        SimpleUser user = createAndSelectUser();
        SimpleUser user_selected_by_login = users.selectUserByLogin(users.selectLoginByEMail(email));
        assertTrue(users_has_same_fields(user, user_selected_by_login));
    }

    private SimpleUser createAndSelectUser() {
        Integer userId = createUser(displayName, email, role, login, pwd, active);
        return users.selectUser(userId);
    }

    private Integer createUser(String displayName, String email, String role, String login, String pwd, boolean active) {
        Integer userId = users.createNewUser(displayName, email, role, login, pwd, active);
        createdUsersId.add(userId);
        return userId;
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

    public boolean users_has_same_fields(Object o1, Object o2){
        if(!((o1 instanceof SimpleUser) && (o2 instanceof SimpleUser))) {
            return false;
        }
        SimpleUser user1 = (SimpleUser)o1;
        SimpleUser user2 = (SimpleUser)o2;
        return user1.getDisplay_name().equals(user2.getDisplay_name())
                && user1.getDisplay_role().equals(user2.getDisplay_role())
                && user1.getEmail().equals(user2.getEmail())
                && user1.getRoles().equals(user2.getRoles())
                && user1.getStatus_id() == user2.getStatus_id()
                && user1.getUser_id() == user2.getUser_id();
    }

    private static class DBConfig implements DataConfigProvider {
        public DataConfigProvider.Conf getConfig(Class clazz) {
            return new Conf("localhost", "5432", "sample", "postgres", "password123");
        }
    }
}
