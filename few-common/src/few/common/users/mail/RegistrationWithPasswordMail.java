package few.common.users.mail;

import few.common.mail.MailTemplate;
import few.common.properties.Props;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 04.11.11
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */
public class RegistrationWithPasswordMail extends MailTemplate {

    public static final String DISPLAY_NAME = "name";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    public static final String TITLE = "Регистрация на " + Props.SITE_NAME.getString();
    public static final String TEMPLATE = "/mail-templates/registration_with_password.ftl";

    public RegistrationWithPasswordMail(String displayName, String login, String password) {
        super(TITLE, TEMPLATE);
        map.put(DISPLAY_NAME, displayName);
        map.put(LOGIN, login);
        map.put(PASSWORD, password);
    }


}
