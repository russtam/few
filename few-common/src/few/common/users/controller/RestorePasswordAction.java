package few.common.users.controller;

import few.*;
import few.common.users.persistence.SimpleUser;
import few.common.users.service.AccountService;
import few.common.users.service.UserService;
import few.utils.Utils;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 31.10.11
 * Time: 21:46
 * To change this template use File | Settings | File Templates.
 */
@ActionClass(action = "restore_password")
public class RestorePasswordAction {

    @ActionMethod ( _default = true )
    public void service( ) {
    }

    @ActionMethod
    public void restore(
            @RequestParameter(name = "email") String email
    ) throws ServletException, IOException {

        email = email.trim();
        SimpleUser user = UserService.get().selectUserByEMail(email);

        if( user != null ) {
            if( user.status_id == SimpleUser.ACTIVE ) {
                if( AccountService.get().restorePassword(email) )
                    Context.get().addMessage( new Message(Message.INFO, "На ваш email отправлен новый пароль и ссылка для его активации.") );
                else
                    Context.get().addMessage( new Message(Message.ERROR, "Ошибка отправки email.") );
            } else {
                if( user.status_id == SimpleUser.NOT_ACTIVE ) {
                    Context.get().addMessage( new Message(Message.ERROR,  "Пользователь ещё не активирован. Проверьте почту.") );
                }
                if( user.status_id == SimpleUser.BLOCKED ) {
                    Context.get().addMessage( new Message(Message.ERROR,  "Пользователь заблокирован. Обратитесь к администратору сайта.") );
                }
            }

        } else {
            Context.get().addMessage( new Message(Message.ERROR, "Пользователя с таким email не существует.") );
        }

    }

    @ActionMethod
    public ActionResponse activate(
            @RequestParameter(name = "key") String secureKey
    ) {
        if( AccountService.get().restorePasswordActivate(secureKey) ) {
            Context.get().addMessage( new Message(Message.INFO, "Ваш новый пароль активирован, можете заходить.") );
            return ActionResponse.view("login");
        } else {
            Context.get().addMessage( new Message(Message.ERROR, "Ссылка устарела. Попробуйте запросить восстановление пароля ещё раз.") );
        }
        return ActionResponse._default();
    }

}
