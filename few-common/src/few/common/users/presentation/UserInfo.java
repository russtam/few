package few.common.users.presentation;

import few.Context;
import few.ModelBean;
import few.common.users.controller.LoginAction;
import few.common.users.persistence.SimpleUser;
import few.common.users.service.UserService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 31.10.11
 * Time: 23:53
 * To change this template use File | Settings | File Templates.
 */
@ModelBean(name = "userInfo")
public class UserInfo {
    public boolean signedIn;
    public Integer userId;
    public String displayName;
    public Set<String> roles;

    public boolean isSignedIn() {
        return signedIn;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isUserInRole(String role) {
        if( roles != null )
            return roles.contains(role);
        return false;
    }

    private final static UserService users = UserService.get();
    public static UserInfo build() {
        UserInfo ret = new UserInfo();
        Integer user_id = (Integer) Context.get().getSession().getAttribute(LoginAction.USER_ID_SESSION_KEY);
        ret.signedIn =  Context.get().isSignedIn();
        if( user_id != null ) {
            SimpleUser user = users.selectUser(user_id);
            ret.displayName = user.display_name;
            ret.roles = new HashSet<String>();
            ret.roles = user.roles;
        }

        return ret;
    }

}
