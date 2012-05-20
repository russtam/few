package few.support;

import few.Context;
import few.ModelBean;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

import javax.servlet.http.HttpServletRequest;

/**
 * User: gerbylev
 * Date: 23.12.11
 */
@ModelBean(name = "ctx")
public class ContextPath implements TemplateScalarModel {

    public static ContextPath build() {
        return new ContextPath();
    }

    @Override
    public String getAsString() throws TemplateModelException {
        return Context.get().getServletContext().getContextPath();
    }
}
