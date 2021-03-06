package few.common.mail;

import few.common.PropKeys;
import few.common.cms.dto.SimpleText;
import few.common.cms.dao.CMSService;
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
 * User: gerbylev
 * Date: 04.11.11
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
    CMSService cmsService = CMSService.get();
    public String processTempate(MailTemplate mailTemplate) {

        String path = mailTemplate.getTemplate();
        if( path.startsWith("/mail-templates/") && path.endsWith(".ftl") ) {
            path = path.substring("/mail-templates/".length(), path.length() - ".ftl".length());

            // TODO : it needs to more deeper integration MailService with CMS.
            SimpleText text = cmsService.selectSimpleText("mails", path);
            if( text != null ) {
                return freemarker.processStringTemplate(text.text, mailTemplate.getParameters());
            }
        }

        return freemarker.processTemplate(
                mailTemplate.getTemplate(),
                mailTemplate.getParameters());
    }

    public int sendEmailSimple(String email,
                                MailTemplate mailTemplate) {

        try {
            log.info("send email '" + mailTemplate.getSubject() + "'(" + mailTemplate.getTemplate() + ") to " + email);

            String content = processTempate(mailTemplate);

            sendEmailSimple(email, mailTemplate.getSubject(), content, mailTemplate.getContentType());

            return content.getBytes().length;
        } catch (Throwable e) {
            log.log(Level.SEVERE, "can not send email to " + email, e);
            return -1;
        }
    }

    static {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    }

    private void sendEmailSimple(String email,
                                String subject,
                                String content, String contentType) throws Exception {
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
