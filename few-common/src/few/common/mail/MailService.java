package few.common.mail;

import few.common.properties.Props;
import few.core.ServiceRegistry;
import few.services.FreemarkerService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.Security;
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
            log.log(Level.SEVERE, "can not send email to " + email + " through " + Props.SMTP_HOST.getString() + ":" + Props.SMTP_PORT.getString(), e);
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
        props.put("mail.smtp.host", Props.SMTP_HOST.getString());
        props.put("mail.smtp.port", Props.SMTP_PORT.getString());

        Session session = Session.getInstance(props);

        session.setDebug(true);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(Props.MAILS_FROM.getString()));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        msg.setSubject(subject);
        msg.setContent(content, contentType);

        Transport t = session.getTransport("smtp");
        if( Props.SMTP_USER.exists() )
            t.connect(Props.SMTP_USER.getString(), Props.SMTP_PASSWORD.getString());
        else
            t.connect();

        t.sendMessage(msg, new Address[] {
                new InternetAddress(email)
            }
        );
        if( Props.MAIL_COPY.exists() ) {
            msg.setSubject( "Копия от (" + msg.getRecipients(Message.RecipientType.TO)[0].toString()+ ") - " + msg.getSubject() );
            msg.setRecipient( Message.RecipientType.TO, new InternetAddress(email) );
            t.sendMessage(msg, new Address[]{
                    new InternetAddress(Props.MAIL_COPY.getString())
            }
            );
        }
    }

    static private final Logger log = Logger.getLogger(MailService.class.getName());
}
