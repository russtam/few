package few.common.users.controller;

import few.*;
import few.common.audit.dao.AuditService;
import few.common.audit.service.AuditKeys;
import few.common.users.dao.UserService;
import few.common.users.dto.CustomField;
import few.common.users.service.AccountService;
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
@Controller(name = "registration")
public class RegistrationAction {

    private static UserService users = UserService.get();
    private static AccountService account = AccountService.get();
    private static AuditService auditService = AuditService.get();

    @Action()
    public ActionResponse save(
            @RequestParameter(name = "email") String email,
            @RequestParameter(name = "login", required = false) String login,
            @RequestParameter(name = "name", required = false) String name,
            @RequestParameter(name = "password", required = false) String password,
            @RequestParameter(name = "password1", required = false) String password1,
            @RequestParameters Map<String, String[]> fields

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
        if( profile == null ) {
            Context.get().addMessage(new Message(Message.ERROR, "all", "Некорректно заполнен профиль." ));
            return ActionResponse._default();
        }

        if( Utils.isNull(password) ) {
            account.registerUserSendPassword(email, name, login, profile);
        } else {
            account.registerUserWithActivation(email, name, login, password, profile);
        }
        auditService.insertActivity(
                AuditKeys.NORMAL, AuditKeys.REGISTRATION, email);

        Context.get().addMessage(new Message(Message.INFO, "all", "Аккаунт успешно создан, проверьте почту." ));

        return ActionResponse.page("login");
    }


}
