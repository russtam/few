package few.common.users.persistence;

import java.util.Set;

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

    private volatile int hashcode;

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

    @Override public boolean equals(Object o){
        if(o == this) {
            return true;
        }
        if(!(o instanceof SimpleUser)) {
            return false;
        }
        SimpleUser user = (SimpleUser)o;
        return user.display_name.equals(display_name)
                && user.display_role.equals(display_role)
                && user.email.equals(email)
                && user.roles.equals(roles)
                && user.status_id == status_id
                && user.user_id == user_id;
    }

    @Override public int hashCode() {
        int result = hashcode;
        if(result == 0) {
            result = 17;
            result = 31 * result + display_name.hashCode();
            result = 31 * result + email.hashCode();
            result = 31 * result + roles.hashCode();
            result = 31 * result + status_id;
            result = 31 * result + user_id;
            hashcode = result;
        }
        return result;
    }
}
