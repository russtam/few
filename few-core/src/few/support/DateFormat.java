package few.support;

import few.ModelBean;
import freemarker.ext.beans.DateModel;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 07.12.11
 * Time: 23:36
 * To change this template use File | Settings | File Templates.
 */
@ModelBean(name = "date_format")
public class DateFormat  implements TemplateMethodModelEx {
    public Object exec(List arguments) throws TemplateModelException {
        if(arguments.size() != 1) {
            throw new TemplateModelException("new_id function must have no argumet!");
        }
        DateModel dm = (DateModel) arguments.get(0);
        Date d = (Date) dm.getWrappedObject();
        String ret = new SimpleDateFormat().format(d);

        return new SimpleScalar(ret);
    }

    public static DateFormat build() {
        return new DateFormat();
    }

}
