package few.common.users.controller;

import few.*;
import few.common.audit.service.AuditKeys;
import few.common.audit.service.AuditService;
import few.common.mail.MailService;
import few.common.users.mail.RegistrationWithPasswordMail;
import few.common.users.persistence.CustomField;
import few.common.users.persistence.SimpleUser;
import few.common.users.service.AccountService;
import few.common.users.service.UserProfileService;
import few.common.users.service.UserService;
import few.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 25.12.11
 * Time: 4:18
 * To change this template use File | Settings | File Templates.
 */
@ActionClass(action = "user_list")
@Restriction(roles = "admin")
public class UserListAction {

    @ActionMethod(_default = true)
    public void render() {
    }

    private UserService userService = UserService.get();
    private MailService mailService = MailService.get();
    private AuditService auditService = AuditService.get();

    @ActionMethod
    public ActionResponse delete(
            @RequestParameter(name = "user_id") Integer user_id,
            @RequestParameter(name = "delete") String fake) {

        userService.deleteUser(user_id);
        auditService.insertActivity(AuditKeys.NORMAL, AuditKeys.USER_DELETE, String.valueOf(user_id));

        return ActionResponse.redirect("user_list");
    }

    @ActionMethod
    public ActionResponse ban(
            @RequestParameter(name = "user_id") Integer user_id,
            @RequestParameter(name = "ban") String fake
    ) {
        SimpleUser user = userService.selectUser(user_id);
        user.status_id = 2;
        userService.updateSimpleUser(user);

        Context.get().addMessage( new Message(Message.INFO, "Пользователь заблокирован.") );
        auditService.insertActivity(AuditKeys.NORMAL, AuditKeys.USER_DISABLE, String.valueOf(user_id));

        return here(user_id);
    }

    private ActionResponse here(Integer user_id) {
        return ActionResponse.redirect(new MyURL("/user_list").p("user_id", String.valueOf(user_id)));
    }

    @ActionMethod
    public ActionResponse unban(
            @RequestParameter(name = "user_id") Integer user_id,
            @RequestParameter(name = "unban") String fake
    ) {
        SimpleUser user = userService.selectUser(user_id);
        user.status_id = 1;
        userService.updateSimpleUser(user);
        auditService.insertActivity(AuditKeys.NORMAL, AuditKeys.USER_ACTIVATE, String.valueOf(user_id));

        Context.get().addMessage( new Message(Message.INFO, "Пользователь активирован.") );
        return here(user_id);
    }

    @ActionMethod
    public ActionResponse update(
            @RequestParameter(name = "user_id") Integer user_id,
            @RequestParameter(name = "update") String fake,

            @RequestParameter(name = "login", required = false) String login,
            @RequestParameter(name = "display_name") String display_name,
            @RequestParameter(name = "email") String email,
            @RequestParameter(name = "status") Integer status,
            @RequestParameter(name = "display_role") String display_role,
            @RequestParameters Map<String, String> fields
    ) {
        SimpleUser user = userService.selectUser(user_id);

        user.display_name = display_name;
        user.email = email;
        user.status_id = status;
        user.display_role = display_role;
        List<CustomField> profile = UserProfileService.get().validateProfile(fields);
        if( profile == null ) {
            Context.get().addMessage( new Message(Message.ERROR, "Вы некорректно заполнили поля формы.") );
            return here(user_id);
        }
        userService.updateSimpleUser(user, profile);

        Context.get().addMessage( new Message(Message.INFO, "Профиль обновлён.") );

        if( Utils.isNotNull(login) ) {
            String _login = userService.selectLoginByUserID(user_id);
            if( !_login.equals(login) ) {
                userService.updateLogin(user_id, login);
                gen_pass(user_id, "");

                Context.get().addMessage(
                        new Message(Message.WARNING, "Логин изменён. Новый пароль выслан на email.")
                );
            }
        }
        auditService.insertActivity(AuditKeys.NORMAL, AuditKeys.USER_UPDATE, String.valueOf(user_id));
        return here(user_id);
    }

    UserProfileService profileService = UserProfileService.get();
    @ActionMethod
    public ActionResponse add(
            @RequestParameter(name = "add") String fake,

            @RequestParameter(name = "login", required = false) String login,
            @RequestParameter(name = "display_name") String display_name,
            @RequestParameter(name = "email") String email,
            @RequestParameter(name = "active") Boolean active,
            @RequestParameter(name = "display_role") String display_role,
            @RequestParameters Map<String, String> fields
    ) {
        Context.get().addMessage( new Message(Message.INFO, "Пользователь создан.") );

        List<CustomField> list = profileService.validateProfile(fields);

        String password = Utils.generateNewPassword();
        Integer user_id = userService.createNewUser(display_name, email, display_role, login, password, active, list);

        RegistrationWithPasswordMail tpl = new RegistrationWithPasswordMail( display_name, login, password);
        mailService.sendEmailSimple(email, tpl);

        Context.get().addMessage(
                new Message(Message.WARNING, "Новый пароль выслан на email.")
        );

        auditService.insertActivity(AuditKeys.NORMAL, AuditKeys.USER_ADD, String.valueOf(user_id));
        return here(user_id);
    }

    @ActionMethod
    public ActionResponse gen_pass(
            @RequestParameter(name = "user_id") Integer user_id,
            @RequestParameter(name = "gen_pass") String fake
    ) {
        new_pass(user_id, Utils.generateNewPassword(), "");
        return here(user_id);
    }

    @ActionMethod
    public ActionResponse new_pass(
            @RequestParameter(name = "user_id") Integer user_id,
            @RequestParameter(name = "password") String password,
            @RequestParameter(name = "new_pass") String fake
    ) {
        SimpleUser user = userService.selectUser(user_id);
        String login = userService.selectLoginByUserID(user_id);
        userService.updateUserPassword(login, password);
        RegistrationWithPasswordMail tpl = new RegistrationWithPasswordMail( user.display_name, login, password);
        mailService.sendEmailSimple(user.email, tpl);

        Context.get().addMessage( new Message(Message.INFO, "Новый пароль выслан пользователю.") );
        auditService.insertActivity(AuditKeys.NORMAL, AuditKeys.USER_NEW_PASSWORD, String.valueOf(user_id));
        return here(user_id);
    }

}
