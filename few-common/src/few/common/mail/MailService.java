package few.common.mail;

import few.common.PropKeys;
import few.core.ServiceRegistry;
import few.services.Configuration;
import few.services.FreemarkerService;
import few.utils.Utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.Security;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 04.11.11
 * Time: 13:41
 * To change this template use File | Settings | File Templates.
 */
public class MailService {

// ==========  STANDART SINGLETON DECLARATION  ==========
    private static MailService instance = new MailService();
    public static MailService get() {
        return instance;
    }
    private MailService() {
    }
// ======================================================

    FreemarkerService freemarker = ServiceRegistry.get(FreemarkerService.class);
    Configuration configuration = ServiceRegistry.get(Configuration.class);

    public boolean sendEmailSimple(String email,
                                MailTemplate mailTemplate) {

        try {
            log.info("send email '" + mailTemplate.getSubject() + "'(" + mailTemplate.getTemplate() + ") to " + email);

            String content =
                    freemarker.processTemplate(
                            mailTemplate.getTemplate(),
                            mailTemplate.getParameters());

            sendEmailSimple(email, mailTemplate.getSubject(), content, mailTemplate.getContentType());

            return true;
        } catch (Throwable e) {
            log.log(Level.SEVERE, "can not send email to " + email, e);
            return false;
        }
    }

    static {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    }

    private void sendEmailSimple(String email,
                                String subject,
                                String content, String contentType ) throws Exception {
        Properties props = new Properties();

        for (Iterator<String> i = configuration.propertyNames().iterator(); i.hasNext(); ) {
            String prop = i.next();
            if( prop.startsWith("mail.") ) {
                props.put(prop, configuration.getProperty(prop));
            }
        }

        Session session = Session.getInstance(props);

        String debug = configuration.getProperty("mail.debug.enable");
        session.setDebug("true".equals(debug));

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(configuration.getProperty(PropKeys.MAILS_SENDER_EMAIL)));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        msg.setSubject(subject);
        msg.setContent(content, contentType);

        String user = configuration.getProperty("mail.smtp.user");
        String pass = configuration.getProperty("mail.smtp.password");

        Transport t = session.getTransport("smtp");
        if( Utils.isNotNull(user) )
            t.connect(user, pass);
        else
            t.connect();

        t.sendMessage(msg, new Address[] {
                new InternetAddress(email)
            }
        );
        if(Utils.isNotNull(configuration.getProperty(PropKeys.MAILS_COPY_EMAIL))) {
            msg.setSubject( "Копия от (" + msg.getRecipients(Message.RecipientType.TO)[0].toString()+ ") - " + msg.getSubject() );
            msg.setRecipient( Message.RecipientType.TO, new InternetAddress(email) );
            t.sendMessage(msg, new Address[]{
                    new InternetAddress(
                        configuration.getProperty(PropKeys.MAILS_COPY_EMAIL)
                    )
            }
            );
        }
    }

    static private final Logger log = Logger.getLogger(MailService.class.getName());
}
