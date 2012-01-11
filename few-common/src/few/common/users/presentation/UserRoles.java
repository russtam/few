package few.common.users.presentation;

import few.ModelBean;
import few.common.users.service.UserService;
import few.utils.ListWrapper;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 25.12.11
 * Time: 3:45
 * To change this template use File | Settings | File Templates.
 */
@ModelBean(name = "user_roles")
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
