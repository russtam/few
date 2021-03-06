package few.common.users.model;

import few.ModelBean;
import few.RequestParameter;
import few.common.users.dao.UserService;
import few.common.users.dto.SimpleUser;

import java.util.Set;

/**
 * User: igor
 * Date: 25.12.11
 */
@ModelBean(name = "user_profile", permission = "admin")
public class UserProfile extends UserInfo{

    private Integer status;

    private UserProfile(Integer userId, Integer status, String login, String displayName, String email, String display_role, Set<String> roles, few.common.users.dto.UserProfile profile) {
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
            few.common.users.dto.UserProfile profile = users.selectUserProfile(user.user_id);
            return new UserProfile(
                    user_id, user.status_id, login, user.display_name, user.email, user.display_role, user.roles, profile
            );
        } else {
            return null;
        }
    }
}
