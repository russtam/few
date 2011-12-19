package few.common.mail;

import few.common.properties.Props;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 04.11.11
 * Time: 13:49
 * To change this template use File | Settings | File Templates.
 */
public abstract class MailTemplate {

    protected HashMap<String, Object> map = new HashMap<String, Object>();
    private String subject;
    private String template;
    protected MailTemplate(String subject, String template) {
        this.subject = subject;
        this.template = template;
        map.put("site", Props.SITE_NAME.getString());
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
