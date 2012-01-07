package few.common.users.controller;

import few.*;
import few.common.audit.service.AuditKeys;
import few.common.audit.service.AuditService;
import few.common.users.persistence.CustomField;
import few.common.users.service.AccountService;
import few.common.users.service.UserProfileService;
import few.common.users.service.UserService;
import few.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 31.10.11
 * Time: 21:45
 * To change this template use File | Settings | File Templates.
 */
@ActionClass(action = "registration")
public class RegistrationAction {

    private static UserService users = UserService.get();
    private static AccountService account = AccountService.get();
    private static AuditService auditService = AuditService.get();

    @ActionMethod(_default = true)
    public ActionResponse _default() {
        if( Context.get().isSignedIn() )
            return ActionResponse.redirect("user_profile");
        return ActionResponse._default();
    }

    @ActionMethod()
    public ActionResponse service(
            @RequestParameter(name = "email") String email,
            @RequestParameter(name = "login") String login,
            @RequestParameter(name = "name") String name,
            @RequestParameter(name = "password") String password,
            @RequestParameter(name = "password1") String password1,
            @RequestParameters Map<String, String> fields

    ) throws ServletException, IOException {

        if( Utils.isNull(email) ) {
            Context.get().addMessage(new Message(Message.ERROR, "email", "Заполните поле email"));
            return ActionResponse._default();
        }

        if( !Utils.checkEmail(email) ) {
            Context.get().addMessage(new Message(Message.ERROR, "email", "Введите email в формате aa@bb.cc"));
            return ActionResponse._default();
        }

        if( users.selectUserByEMail(email) != null ) {
            Context.get().addMessage(new Message(Message.ERROR, "email", "Пользователь с таким email уже зарегистрирован"));
            return ActionResponse._default();
        }


        if( Utils.isNull(login) )
            login = email;

        if( users.selectUserByLogin(login) != null ) {
            Context.get().addMessage(new Message(Message.ERROR, "login", "Такой логин уже используется"));
            return ActionResponse._default();
        }

        if( Utils.isNull(name) )
            name = login;


        if( Utils.isNotNull(password) || Utils.isNotNull(password1) ) {
            // TODO : validate
            if( password != null && password.equals(password1) ) {

            } else {
                Context.get().addMessage(new Message(Message.ERROR, "password", "Пароли не совпадают" ));
            }
        }

        List<CustomField> profile = UserProfileService.get().validateProfile(fields);

        if( Utils.isNull(password) ) {
            account.registerUserSendPassword(email, name, login, profile);
        } else {
            account.registerUserWithActivation(email, name, login, password, profile);
        }
        auditService.insertActivity(
                AuditKeys.NORMAL, AuditKeys.REGISTRATION, email);

        Context.get().addMessage(new Message(Message.INFO, "all", "Аккаунт успешно создан, проверьте почту." ));

        return ActionResponse.view("login");
    }


}
