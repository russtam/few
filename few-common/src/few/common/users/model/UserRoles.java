package few.common.users.model;

import few.ModelBean;
import few.common.users.dao.UserService;
import few.utils.ListWrapper;

import java.util.List;

/**
 * User: igor
 * Date: 25.12.11
 */
@ModelBean(name = "user_roles", permission = "admin")
public class UserRoles extends ListWrapper<String> {


    private UserRoles(List<String> strings) {
        super(strings);
    }

    private static UserService userService = UserService.get();
    public static UserRoles build() {
        UserRoles ret =
                new UserRoles(userService.selectDisplayRoles());
        return ret;
    }
}
