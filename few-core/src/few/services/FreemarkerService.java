package few.services;

import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 19.12.11
 * Time: 1:11
 * To change this template use File | Settings | File Templates.
 */
public interface FreemarkerService {
    void processTemplate(String template, Writer writer);

    void processTemplate(String template, Writer writer, Object parameters);

    String processStringTemplate(String code, Object parameters);

    String processTemplate(String template);

    String processTemplate(String template, Object parameters);
}
