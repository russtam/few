package few.support;

import few.ModelBean;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateModelException;

import javax.servlet.http.HttpServletRequest;

/**
 * User: gerbylev
 * Date: 23.12.11
 */
@ModelBean(name = "ajax")
public class Ajax implements TemplateBooleanModel {

    private boolean value;

    public Ajax(boolean value) {
        this.value = value;
    }

    public static Ajax build(HttpServletRequest request) {
        return new Ajax( request.getHeader("X-Requested-With") != null );
    }

    public boolean getAsBoolean() throws TemplateModelException {
        return value;
    }
}
