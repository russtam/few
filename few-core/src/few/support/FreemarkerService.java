package few.support;

import few.Context;
import few.needed.OuterFactory;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 04.11.11
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */
public class FreemarkerService {

// ==========  STANDART SINGLETON DECLARATION  ==========
    private static FreemarkerService instance = new FreemarkerService();
    public static FreemarkerService get() {
        return instance;
    }
    private FreemarkerService() {
    }
// ======================================================


    private int autocheckInterval = OuterFactory.get().getConfiguration().getInteger(few.needed.Configuration.AUTOCHECK_INTERVAL);

    private Configuration cfg;
    public void initFreemarker(ServletContext context) {
        cfg = new Configuration();
        // - Templates are stoted in the WEB-INF/templates directory of the Web app.
        cfg.setServletContextForTemplateLoading(
                context, "");
        // - Set update dealy to 0 for now, to ease debugging and testing.
        //   Higher value should be used in production environment.
        cfg.setTemplateUpdateDelay(autocheckInterval);
        // - Set an error handler that prints errors so they are readable with
        //   a HTML browser.
        cfg.setTemplateExceptionHandler(
                TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        // - Use beans wrapper (recommmended for most applications)
        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        // - Set the default charset of the template files
        cfg.setDefaultEncoding("UTF-8");
        // - Set the charset of the output. This is actually just a hint, that
        //   templates may require for URL encoding and for generating META element
        //   that uses http-equiv="Content-type".
        cfg.setOutputEncoding("UTF-8");
        // - Set the default locale
        cfg.setLocale(Locale.US);
    }

    public void initFreemarker(File file) throws IOException {
        cfg = new Configuration();
        // - Templates are stoted in the WEB-INF/templates directory of the Web app.
        cfg.setDirectoryForTemplateLoading(file);
        // - Set update dealy to 0 for now, to ease debugging and testing.
        //   Higher value should be used in production environment.
        cfg.setTemplateUpdateDelay(autocheckInterval);
        // - Set an error handler that prints errors so they are readable with
        //   a HTML browser.
        cfg.setTemplateExceptionHandler(
                TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        // - Use beans wrapper (recommmended for most applications)
        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        // - Set the default charset of the template files
        cfg.setDefaultEncoding("UTF-8");
        // - Set the charset of the output. This is actually just a hint, that
        //   templates may require for URL encoding and for generating META element
        //   that uses http-equiv="Content-type".
        cfg.setOutputEncoding("UTF-8");
        // - Set the default locale
        cfg.setLocale(Locale.US);
    }


    public void processTemplate(String template, Writer writer) {
        processTemplate(template, writer, Context.get().getModel());
    }

    public void processTemplate(String template, Writer writer, Object parameters) {
        try {
            Template t = cfg.getTemplate(template);
            t.process(parameters, writer);
        } catch (Exception e) {
            log.log(Level.SEVERE, "", e);
        }
    }

    public String processTemplate(String template) {
        StringWriter sw = new StringWriter();
        processTemplate(template, sw);
        return sw.toString();
    }

    public String processTemplate(String template, Object parameters) {
        StringWriter sw = new StringWriter();
        processTemplate(template, sw, parameters);
        return sw.toString();
    }

    Logger log = Logger.getLogger(FreemarkerService.class.getName());
}
