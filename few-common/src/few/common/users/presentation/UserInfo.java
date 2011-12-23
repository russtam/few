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
    private boolean signedIn;
    private Integer userId;
    private String displayName;
    private String login;
    private String email;
    private Set<String> roles;

    public UserInfo(Integer userId, String login, String displayName, String email, Set<String> roles) {
        this.signedIn = true;
        this.userId = userId;
        this.displayName = displayName;
        this.login = login;
        this.email = email;
        this.roles = roles;
    }

    public UserInfo() {
        this.signedIn = false;
    }

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

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    private final static UserService users = UserService.get();
    public static UserInfo build() {
        Integer user_id = (Integer) Context.get().getSession().getAttribute(LoginAction.USER_ID_SESSION_KEY);
        if( user_id != null ) {
            SimpleUser user = users.selectUser(user_id);
            String login = users.selectLoginByUserID(user.user_id);
            return new UserInfo(
                    user_id, login, user.display_name, user.email, user.roles
            );
        } else {
            return new UserInfo();
        }
    }

}
