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
    public Set<String> roles;
}
