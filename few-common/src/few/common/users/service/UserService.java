package few.common.users.service;

import few.common.BaseMyBatisServiceImpl;
import few.common.users.persistence.CustomField;
import few.common.users.persistence.SimpleUser;
import few.common.users.persistence.UserProfile;
import few.utils.MapBuilder;
import few.utils.Utils;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 28.10.11
 * Time: 21:29
 * To change this template use File | Settings | File Templates.
 */
public class UserService extends BaseMyBatisServiceImpl {

    private static UserService service = new UserService();
    public static UserService get() {
        return service;
    }
    private UserService() {
    }

    public SimpleUser selectUserBySimpleAuth(String login, String password) {
        return (SimpleUser)
            session().selectOne("few.common.selectSimpleUser", new MapBuilder()
                    .add("login", login)
                    .add("password", Utils.produceSHA1fromPassword(login, password))
            );
    }

    public SimpleUser selectUserByLogin(String login) {
        return (SimpleUser)
            session().selectOne("few.common.selectSimpleUser", new MapBuilder()
                    .add("login", login)
            );
    }

    public SimpleUser selectUserByEMail(String email) {
        return (SimpleUser)
            session().selectOne("few.common.selectSimpleUser", new MapBuilder()
                    .add("email", email)
            );
    }

    public SimpleUser selectUser(int user_id) {
        return (SimpleUser)
            session().selectOne("few.common.selectSimpleUser", new MapBuilder()
                    .add("user_id", user_id)
            );
    }

    public List<SimpleUser> selectUsers() {
        return
            session().selectList("few.common.selectSimpleUser", new HashMap());
    }

    public List<SimpleUser> selectUsersByRole(String roleName) {
        return
            session().selectList("few.common.selectSimpleUser", new MapBuilder()
                    .add("display_role", roleName));
    }

    public List<String> selectDisplayRoles() {
        return
            session().selectList("few.common.selectDisplayRoles");
    }

    public Integer createNewUser(String display_name, String email, String role, String login, String password, boolean active,
        List<CustomField> profile) {
        SqlSession session = session();
        int user_id = (Integer)session.selectOne("few.common.select_uid");

        session.insert("few.common.insertSimpleUser", new MapBuilder()
                .add("id", user_id)
                .add("display_name", display_name)
                .add("role_id", role)
                .add("email", email)
                .add("status_id", active ? 1 : 0)
                .add("registration_time", Utils.curDate())
        );

        session.insert("few.common.insertLoginPassword", new MapBuilder()
                .add("user_id",     user_id)
                .add("login",       login)
                .add("password",    Utils.produceSHA1fromPassword(login, password))
        );

        for (CustomField cf : profile) {
            session().insert("insertUserProfileField",
                    new MapBuilder().add("user_id",user_id).add("field_id",cf.field_id).add("value",cf.value));
        }

        session.commit();

        return user_id;
    }

    public void deleteUser(int user_id) {
        session().delete("few.common.deleteUser", user_id);
        session().commit();
    }


    public void updateUserPassword(String login, String password) {
        session().update("few.common.updateSimpleUserPasswordByLogin", new MapBuilder()
                .add("login", login)
                .add("password", Utils.produceSHA1fromPassword(login, password))
        );
        session().commit();
    }

    public void updateSimpleUser(SimpleUser user) {
        session().update("few.common.updateSimpleUser", new MapBuilder()
                .add("user_id", user.user_id)
                .add("email", user.email)
                .add("display_name", user.display_name)
                .add("display_role", user.display_role)
                .add("status_id", user.status_id)
        );

        session().commit();
    }

    public void updateSimpleUser(SimpleUser user, List<CustomField> profile) {
        session().update("few.common.updateSimpleUser", new MapBuilder()
                .add("user_id", user.user_id)
                .add("email", user.email)
                .add("display_name", user.display_name)
                .add("display_role", user.display_role)
                .add("status_id", user.status_id)
        );

        session().delete("deleteUserProfile", user.user_id);
        for (CustomField cf : profile) {
            session().insert("insertUserProfileField",
                    new MapBuilder().add("user_id",user.user_id).add("field_id",cf.field_id).add("value",cf.value));
        }

        session().commit();
    }

    public void updateUserProfile(int user_id, List<CustomField> profile) {
        session().delete("deleteUserProfile", user_id);
        for (CustomField cf : profile) {
            session().insert("insertUserProfileField",
                    new MapBuilder().add("user_id",user_id).add("field_id",cf.field_id).add("value",cf.value));
        }

        session().commit();
    }

    public void updateLogin(Integer user_id, String login) {
        session().update("few.common.updateSimpleUserLogin", new MapBuilder()
                .add("user_id", user_id)
                .add("login", login)
        );
        session().commit();
    }

    public void activateUser(int user_id) {
        session().update("few.common.updateUserStatus", new MapBuilder()
                .add("id", user_id)
                .add("status_id", SimpleUser.ACTIVE)
        );
        session().commit();
    }

    public void updateLastLogin(int user_id) {
        session().update("few.common.updateLastLogin", new MapBuilder()
                .add("user_id", user_id)
        );
        session().commit();
    }


    public String selectLoginByUserID(Integer user_id) {
        return (String) session().selectOne("few.common.selectLoginByUserID", user_id);
    }

    public String selectLoginByEMail(String email) {
        return (String) session().selectOne("selectLoginByEMail", email);
    }

    public UserProfile selectUserProfile(Integer user_id) {
        return (UserProfile) session().selectOne("selectUserProfile",
                new MapBuilder().add("id", user_id));
    }
    public List<UserProfile> selectUserProfiles() {
        return session().selectList("selectUserProfile");
    }
}
