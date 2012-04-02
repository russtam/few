package few.common.users.model;

import few.Context;
import few.ModelBean;
import few.common.users.controller.LoginAction;
import few.common.users.dao.UserService;
import few.common.users.dto.*;
import few.common.users.dto.UserProfile;
import few.common.users.service.UserProfileService;

import java.util.*;

/**
 * User: gerbylev
 * Date: 31.10.11
 */
@ModelBean(name = "userInfo")
public class UserInfo {
    private boolean signed_in;
    private Integer user_id;
    private String display_name;
    private String login;
    private String email;
    private String display_role;
    private Set<String> roles;
    private Map<String, String> profile;

    public UserInfo(Integer userId, String login, String displayName, String email, String display_role, Set<String> roles, UserProfile fields) {
        this.signed_in = true;
        this.user_id = userId;
        this.display_name = displayName;
        this.login = login;
        this.email = email;
        this.display_role = display_role;
        this.roles = roles;
        if( fields != null)
            this.profile = fields.getFieldsMap();
        else
            this.profile = Collections.emptyMap();
    }

    public UserInfo() {
        this.signed_in = false;
    }

    public boolean isSigned_in() {
        return signed_in;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public String getDisplay_name() {
        return display_name;
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

    public String getDisplay_role() {
        return display_role;
    }

    public Map<String, String> getProfile() {
        return profile;
    }

    public String field(String id) {
        String val = profile.get(id);
        if( val != null )
            return customProfile.getCustomFields().get(id).format( profile.get(id) );
        return null;
    }

    private final static UserProfileService customProfile = UserProfileService.get();
    private final static UserService users = UserService.get();
    public static UserInfo build() {
        Integer user_id = (Integer) Context.get().getSession().getAttribute(LoginAction.USER_ID_SESSION_KEY);
        if( user_id != null ) {
            SimpleUser user = users.selectUser(user_id);
            String login = users.selectLoginByUserID(user.user_id);
            few.common.users.dto.UserProfile profile = users.selectUserProfile(user.user_id);
            return new UserInfo(
                    user_id, login, user.display_name, user.email, user.display_role, user.roles, profile
            );
        } else {
            return new UserInfo();
        }
    }

}
