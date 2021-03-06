package few.common.users.model;

import few.ModelBean;
import few.RequestParameter;
import few.common.users.dao.UserService;
import few.common.users.dto.SimpleUser;
import few.utils.ListWrapper;
import few.utils.Utils;

import java.util.List;

/**
 * User: igor
 * Date: 25.12.11
 */
@ModelBean(name = "userList", permission = "admin")
public class UserList extends ListWrapper<SimpleUser>{

    public UserList(List<SimpleUser> userInfos) {
        super(userInfos);
    }

    private static UserService userService = UserService.get();
    public static UserList build(
            @RequestParameter(name = "role", required = false) String roleName
    ) {
        UserList ret;
            if(Utils.isNull(roleName))
            ret = new UserList( userService.selectUsers() );
        else
            ret = new UserList( userService.selectUsersByRole(roleName) );
        return ret;
    }
}
