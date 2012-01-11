package few.common.users.persistence;

import few.common.users.service.UserService;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 12.10.11
 * Time: 17:28
 * To change this template use File | Settings | File Templates.
 */
public class SimpleUser {
    public static final int NOT_ACTIVE = 0;
    public static final int ACTIVE = 1;
    public static final int BLOCKED = 2;

    public int user_id;
    public int status_id;
    public String display_name;
    public String email;
    public String display_role;
    public Set<String> roles;

    public int getUser_id() {
        return user_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplay_role() {
        return display_role;
    }

    public Set<String> getRoles() {
        return roles;
    }

    Map<String, String> profile;
    UserService users = UserService.get();
    // TODO : kill this code
    public Map<String, String> getProfile() {
        if( profile == null ) {
            UserProfile p = users.selectUserProfile(user_id);
            if( p != null)
                profile = p.getFieldsMap();
            else
                profile = Collections.emptyMap();
        }
        return profile;
    }


    @Override public boolean equals(Object o){
        if(o == this) {
            return true;
        }
        if(!(o instanceof SimpleUser)) {
            return false;
        }
        SimpleUser user = (SimpleUser)o;
        return user.user_id == user_id;
    }

    @Override public int hashCode() {
        return user_id;
    }
}
