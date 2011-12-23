package few.common.users.controller;

import few.*;
import few.common.users.persistence.SimpleUser;
import few.common.users.service.UserService;
import few.utils.Utils;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 31.10.11
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
@ActionClass(action = "user_profile")
@Restriction(roles = "user")
public class UserProfileAction {

    UserService userService = UserService.get();

    @ActionMethod(_default = true)
    public ActionResponse _default() {
        if( Context.get().isSignedIn() )
            return ActionResponse._default();
        else
            return ActionResponse.redirect("login");
    }

    @ActionMethod()
    public void changeEMail(
            @RequestParameter(name = "email") String email
    ) throws ServletException, IOException {
        int user_id = Integer.valueOf(Context.get().getUserID());

        if( Utils.isNotNull(email) ) {
            userService.updateEMail(user_id, email);
            Context.get().addMessage(new Message(Message.INFO, "email", "e-mail изменён" ));
        }
    }

    @ActionMethod()
    public void changeDisplayName(
            @RequestParameter(name = "name") String name
    ) throws ServletException, IOException {
        int user_id = Integer.valueOf(Context.get().getUserID());

        if( Utils.isNotNull(name) ) {
            userService.updateDisplayName(user_id, name);
            Context.get().addMessage(new Message(Message.INFO, "display_name", "Имя пользователя изменено" ));
        }
    }

    @ActionMethod()
    public void changePassword(
            @RequestParameter(name = "old_password") String old_password,
            @RequestParameter(name = "password") String password,
            @RequestParameter(name = "password1") String password1
    ) throws ServletException, IOException {
        int user_id = Integer.valueOf(Context.get().getUserID());

        if( Utils.isNotNull(password) || Utils.isNotNull(password1) ) {
            boolean old_password_corrent = true;
            if( userService.selectUserBySimpleAuth(userService.selectLoginByUserID(user_id), old_password) == null ) {
                Context.get().addMessage(new Message(Message.ERROR, "old_password", "Пароль не верный" ));
                old_password_corrent = false;
            }

            // TODO : validate
            if( password != null && password.equals(password1) ) {
                String login = userService.selectLoginByUserID(user_id);
                userService.updateUserPassword(login, password);
                Context.get().addMessage(new Message(Message.INFO, "password", "Пароль обновлён" ));
            } else {
                Context.get().addMessage(new Message(Message.ERROR, "password", "Пароли не совпадают" ));
            }
        }
    }
}
