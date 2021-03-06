package few.common.users.mail;

import few.common.PropKeys;
import few.common.mail.MailTemplate;
import few.core.ServiceRegistry;
import few.services.Configuration;

/**
 * User: gerbylev
 * Date: 04.11.11
 */
public class RestorePasswordMail extends MailTemplate {

    public static final String DISPLAY_NAME = "name";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String LINK = "link";

    public static final String TEMPLATE = "/few-common/mail-templates/restore_password.ftl";
    private static final Configuration conf = ServiceRegistry.get(Configuration.class);

    public RestorePasswordMail(String displayName, String login, String password, String link) {
        super(  "Активизация нового пароля " + conf.getProperty(PropKeys.MAILS_SITE_NAME),
                TEMPLATE);
        map.put(DISPLAY_NAME, displayName);
        map.put(LOGIN, login);
        map.put(PASSWORD, password);
        map.put(LINK, link);
    }



}
