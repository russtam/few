package few.common.users.controller;

import few.*;
import few.common.audit.service.AuditKeys;
import few.common.audit.service.AuditService;
import few.common.users.persistence.SimpleUser;
import few.common.users.service.AccountService;
import few.common.users.service.UserService;
import few.utils.Utils;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 31.10.11
 * Time: 19:05
 * To change this template use File | Settings | File Templates.
 */
@Controller(name = "login")
public class LoginAction {

    public static final String USER_ID_SESSION_KEY = "id";

    private static UserService users = UserService.get();
    private static AuditService auditService = AuditService.get();

    @Action(_default = true)
    public ActionResponse render() {
        if( Context.get().isSignedIn() ) {
            return ActionResponse.redirect("user_profile");
        } else {
            return ActionResponse._default();
        }
    }

    @Action()
    public ActionResponse login(
            @RequestParameter(name = "login") String login,
            @RequestParameter(name = "password") String password,
            @RequestParameter(name = "redirect", required = false) String redirect,
            HttpSession session
    ) throws IOException {
        SimpleUser user = users.selectUserBySimpleAuth(login, password);
        if( user == null ) {
            Context.get().addMessage(new Message(Message.ERROR, "Неправильный логин или пароль."));
            auditService.insertActivity(
                    AuditKeys.NORMAL, AuditKeys.BAD_LOGIN, login);
            return ActionResponse.redirect(new MyURL(false, "/login"));
        }
        if( user.status_id == SimpleUser.NOT_ACTIVE) {
            Context.get().addMessage(new Message(Message.ERROR, "Аккаунт не активирован. Проверьте почту."));
            auditService.insertActivity(
                    AuditKeys.NORMAL, AuditKeys.BAD_LOGIN, login);
            return ActionResponse.redirect(new MyURL(false, "/login"));
        }
        if( user.status_id == SimpleUser.BLOCKED) {
            Context.get().addMessage(new Message(Message.ERROR, "Пользователь заблокирован. Обратитесь к администратору сайта."));
            auditService.insertActivity(
                    AuditKeys.NORMAL, AuditKeys.BAD_LOGIN, login);
            return ActionResponse.redirect(new MyURL(false, "/login"));
        }
        if( user.status_id == SimpleUser.ACTIVE ) {
            session.setAttribute(USER_ID_SESSION_KEY, user.user_id);
            users.updateLastLogin(user.user_id);
            auditService.insertActivity(
                    AuditKeys.NORMAL, AuditKeys.LOGIN, "");

            if(Utils.isNull(redirect))
                return ActionResponse.redirect(
                        new MyURL(false, "/")
                );
            else
                return ActionResponse.redirect(
                        new MyURL(false, redirect)
                );
        }
        throw new IllegalStateException();
    }

    @Action()
    public ActionResponse activate(
            @RequestParameter(name = "key") String securityKey,
            HttpSession session
    ) {
        Integer user_id = AccountService.get().activateUser(securityKey);
        if( user_id != null ) {
            Context.get().addMessage(new Message(Message.INFO, "Ваш аккаунт успешно подтверждён."));
            session.setAttribute(USER_ID_SESSION_KEY, user_id);
            auditService.insertActivity(
                    AuditKeys.NORMAL, AuditKeys.ACTIVATION, "");
            return ActionResponse.redirect("user_profile");
        } else {
            Context.get().addMessage(new Message(Message.INFO, "Ссылка устарела."));
            return ActionResponse._default();
        }
    }

}
