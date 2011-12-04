package few.support;

import few.ModelBean;
import few.utils.JSONRenderer;
import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 04.12.11
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
@ModelBean(name = "json")
public class JSON implements TemplateMethodModelEx {
    public Object exec(List arguments) throws TemplateModelException {
        if(arguments.size() != 1) {
            throw new TemplateModelException("JSON function must have one argumet!");
        }

        Object arg = arguments.get(0);
        if( arg instanceof String )
            arg = Environment.getCurrentEnvironment().getVariable((String) arg);
        if( arg instanceof StringModel)
            arg = ((StringModel) arg).getWrappedObject();

        String res = JSONRenderer.buildJSON(arg);

        return new SimpleScalar(res);
    }

    public static JSON build() {
        return new JSON();
    }
}
