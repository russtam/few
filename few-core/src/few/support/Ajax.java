package few.support;

import few.ModelBean;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateModelException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 23.12.11
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
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
        return false;
    }
}
