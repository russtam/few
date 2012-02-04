package few.services;

import java.io.Writer;

/**
 * User: igor
 * Date: 19.12.11
 */
public interface FreemarkerService {
    boolean checkExists(String template);

    void processTemplate(String template, Writer writer);

    void processTemplate(String template, Writer writer, Object parameters);

    String processStringTemplate(String code, Object parameters);

    String processTemplate(String template);

    String processTemplate(String template, Object parameters);
}
