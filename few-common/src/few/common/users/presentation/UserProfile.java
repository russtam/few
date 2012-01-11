package few.common.users.presentation;

import few.ModelBean;
import few.RequestParameter;
import few.Restriction;
import few.common.users.persistence.CustomField;
import few.common.users.persistence.SimpleUser;
import few.common.users.service.UserService;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 25.12.11
 * Time: 3:33
 * To change this template use File | Settings | File Templates.
 */
@ModelBean(name = "user_profile")
@Restriction(roles = "admin")
public class UserProfile extends UserInfo{

    private Integer status;

    private UserProfile(Integer userId, Integer status, String login, String displayName, String email, String display_role, Set<String> roles, few.common.users.persistence.UserProfile profile) {
        super(userId, login, displayName, email, display_role, roles, profile);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    private final static UserService users = UserService.get();
    public static UserProfile build(
            @RequestParameter(name = "user_id") Integer user_id
    ) {
        SimpleUser user = users.selectUser(user_id);
        if( user != null ) {
            String login = users.selectLoginByUserID(user.user_id);
            few.common.users.persistence.UserProfile profile = users.selectUserProfile(user.user_id);
            return new UserProfile(
                    user_id, user.status_id, login, user.display_name, user.email, user.display_role, user.roles, profile
            );
        } else {
            return null;
        }
    }
}
