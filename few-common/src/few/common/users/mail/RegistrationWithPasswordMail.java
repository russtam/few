package few.common.users.mail;

import few.common.PropKeys;
import few.common.mail.MailTemplate;
import few.core.ServiceRegistry;
import few.services.Configuration;

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

    public static final String TEMPLATE = "/few-common/mail-templates/registration_with_password.ftl";
    private static final Configuration conf = ServiceRegistry.get(Configuration.class);

    public RegistrationWithPasswordMail(String displayName, String login, String password) {
        super(  "Регистрация на " + conf.getProperty(PropKeys.MAILS_SITE_NAME),
                TEMPLATE);
        map.put(DISPLAY_NAME, displayName);
        map.put(LOGIN, login);
        map.put(PASSWORD, password);
    }


}
