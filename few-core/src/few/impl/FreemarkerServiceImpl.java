package few.impl;

import few.Context;
import few.core.ServiceRegistry;
import few.services.FreemarkerService;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: gerbylev
 * Date: 04.11.11
 */
public class FreemarkerServiceImpl implements FreemarkerService {

    private few.services.Configuration conf = ServiceRegistry.get( few.services.Configuration.class );
    private int autocheckInterval = Integer.valueOf(conf.getProperty(few.services.Configuration.AUTOCHECK_INTERVAL));

    private Configuration cfg;
    public FreemarkerServiceImpl(ServletContext context) {
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

    public FreemarkerServiceImpl(File file) throws IOException {
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

    public boolean checkExists(String template) {
        try {
            return cfg.getTemplateLoader().findTemplateSource(template) != null;
        } catch (IOException e) {
            log.log(Level.SEVERE, "", e);
            return false;
        }
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

    public String processStringTemplate(final String template, Object parameters) {
        try {
            StringWriter sw = new StringWriter();
            Configuration tmp = (Configuration) cfg.clone();
            tmp.setTemplateLoader(new TemplateLoader() {
                public Object findTemplateSource(String name) {
                    return 1;
                }
                public long getLastModified(Object templateSource) {
                    return System.currentTimeMillis();
                }
                public Reader getReader(Object templateSource, String encoding) {
                    return new StringReader(template);
                }
                public void closeTemplateSource(Object templateSource) { }
            });

            Template t = tmp.getTemplate("");
            t.process(parameters, sw);
            tmp.clearTemplateCache();
            return sw.toString();
        } catch (Exception e) {
            log.log(Level.SEVERE, "", e);
        }
        return "";
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


    Logger log = Logger.getLogger(FreemarkerServiceImpl.class.getName());
}
