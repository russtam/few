package few.common.mail;

import few.common.PropKeys;
import few.core.ServiceRegistry;
import few.services.Configuration;

import java.util.HashMap;

/**
 * User: gerbylev
 * Date: 04.11.11
 */
public abstract class MailTemplate {

    protected HashMap<String, Object> map = new HashMap<String, Object>();
    private String subject;
    private String template;
    protected String site_name;
    private static Configuration conf = ServiceRegistry.get(Configuration.class);
    protected MailTemplate(String subject, String template) {
        this.subject = subject;
        this.template = template;
        this.site_name = conf.getProperty(PropKeys.MAILS_SITE_NAME);
        map.put("site", site_name);
    }

    public String getSubject() {
        return subject;
    }

    public String getTemplate() {
        return template;
    }

    public Object getParameters() {
        return map;
    }

    public String getContentType() {
        return "text/plain;charset=UTF-8";
    }
}
