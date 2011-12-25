package few.common.users.presentation;

import few.ModelBean;
import few.RequestParameter;
import few.Restriction;
import few.common.users.persistence.SimpleUser;
import few.common.users.service.UserService;
import few.utils.ListWrapper;
import few.utils.Utils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 25.12.11
 * Time: 2:25
 * To change this template use File | Settings | File Templates.
 */
@ModelBean(name = "userList")
@Restriction(roles = "admin")
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
