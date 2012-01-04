package few.common.users.service;

import few.MyURL;
import few.common.mail.MailService;
import few.common.users.mail.RegistrationWithActivationMail;
import few.common.users.mail.RegistrationWithPasswordMail;
import few.common.users.mail.RestorePasswordMail;
import few.common.users.persistence.CustomField;
import few.common.users.persistence.SimpleUser;
import few.utils.Utils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 03.11.11
 * Time: 19:03
 * To change this template use File | Settings | File Templates.
 */
public class AccountService {

// ==========  STANDART SINGLETON DECLARATION  ==========
    private static AccountService instance = new AccountService();
    public static AccountService get() {
        return instance;
    }
    private AccountService() {
    }
// ======================================================

    UserService userService = UserService.get();
    MailService mailService = MailService.get();
    ConfirmationService confirmationService = ConfirmationService.get();
    public static final String DEFAULT_ROLE = "user";

    public boolean registerUserSendPassword(String email, String display_name, String login, List<CustomField> profile) {
        String password = Utils.generateNewPassword();

        Integer user_id = userService.createNewUser(display_name, email, DEFAULT_ROLE, login, password, true, profile);

        RegistrationWithPasswordMail tpl = new RegistrationWithPasswordMail( display_name, login, password);
        return mailService.sendEmailSimple(email, tpl);
    }

    public boolean registerUserWithActivation(String email, String display_name, String login, String password, List<CustomField> profile) {

        Integer user_id = userService.createNewUser(display_name, email, DEFAULT_ROLE, login, password, false, profile);

        String secureKey = confirmationService.createConfirmationKey(new String[]{user_id.toString()}, ConfirmationService.ONE_DAY_TIMEOUT);
        String link = new MyURL("/login").p("key", secureKey).toString();

        RegistrationWithActivationMail tpl = new RegistrationWithActivationMail( display_name, login, password, link);
        return mailService.sendEmailSimple(email, tpl);
    }

    public Integer activateUser(String secureKey) {

        String []params = confirmationService.useConfirmationKey(secureKey);
        if( params != null ) {
            Integer user_id = Integer.valueOf(params[0]);
            userService.activateUser(user_id);
            return user_id;
        }
        return null;

    }


    public boolean restorePassword(String email) {
        String password = Utils.generateNewPassword();
        String login = userService.selectLoginByEMail(email);

        SimpleUser su = userService.selectUserByLogin(login);

        String secureKey = confirmationService.createConfirmationKey( new String[]{login, password}, ConfirmationService.ONE_DAY_TIMEOUT);

        String link = new MyURL("/restore_password").p("key", secureKey).toString();

        RestorePasswordMail tpl = new RestorePasswordMail( su.display_name, login, password, link);
        return mailService.sendEmailSimple(su.email, tpl);
    }

    public boolean restorePasswordActivate(String secureKey) {

        String []params = confirmationService.useConfirmationKey(secureKey);
        if( params != null ) {
            String login = params[0];
            String password = params[1];
            userService.updateUserPassword(login, password);
            return true;
        } else
            return false;

    }

}
