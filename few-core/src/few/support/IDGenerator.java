package few.support;

import few.ModelBean;
import few.utils.JSONRenderer;
import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 05.12.11
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
@ModelBean(name = "new_id")
public class IDGenerator implements TemplateMethodModelEx {

    private int id = 0;
    public Object exec(List arguments) throws TemplateModelException {
        if(arguments.size() != 0) {
            throw new TemplateModelException("new_id function must have no argumet!");
        }

        return new SimpleNumber(++id);
    }

    public static IDGenerator build() {
        return new IDGenerator();
    }
}
