package few.common.users.controller;

import few.*;
import few.common.audit.dao.AuditService;
import few.common.audit.service.AuditKeys;
import few.common.users.dao.UserService;
import few.common.users.dto.CustomField;
import few.common.users.dto.SimpleUser;
import few.common.users.service.UserProfileService;
import few.utils.Utils;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * User: gerbylev
 * Date: 31.10.11
 */
@Controller(name = "user_profile", permission = "user")
public class UserProfileAction {

    private static UserService userService = UserService.get();
    private static AuditService auditService = AuditService.get();

    @Action()
    public void changeEMail(
            @RequestParameter(name = "email") String email
    ) throws ServletException, IOException {
        int user_id = Integer.valueOf(Context.get().getUserID());

        if( Utils.isNotNull(email) ) {
            SimpleUser user = userService.selectUser(user_id);
            user.email = email;
            userService.updateSimpleUser(user);
            auditService.insertActivity(AuditKeys.MINOR, AuditKeys.UPDATE_PROFILE, "changeEMail");
            Context.get().addMessage(new Message(Message.INFO, "e-mail изменён" ));
        }
    }

    @Action()
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
                auditService.insertActivity(AuditKeys.MINOR, AuditKeys.UPDATE_PROFILE, "changePassword");
                Context.get().addMessage(new Message(Message.INFO, "Пароль обновлён" ));
            } else {
                Context.get().addMessage(new Message(Message.ERROR, "password", "Пароли не совпадают" ));
            }
        }
    }

    @Action
    public void updateProfile(
            @RequestParameter(name = "name") String name,
            @RequestParameters Map<String, String[]> fields
    ) {
        List<CustomField> profile = UserProfileService.get().validateProfile(fields);
        if( profile == null ) {
            Context.get().addMessage( new Message(Message.ERROR, "Вы некорректно заполнили поля формы.") );
            return ;
        }
        SimpleUser user = userService.selectUser(Integer.parseInt(Context.get().getUserID()));
        user.display_name = name;
        userService.updateSimpleUser(user, profile);
        auditService.insertActivity(AuditKeys.MINOR, AuditKeys.UPDATE_PROFILE, "updateProfile");
        Context.get().addMessage(new Message(Message.INFO, "Профиль обновлён" ));
    }

}
