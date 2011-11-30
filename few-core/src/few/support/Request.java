package few.support;

import few.Context;
import few.ModelBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 30.11.11
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
@ModelBean(name = "request")
public class Request extends HttpServletRequestWrapper{

    private Request(HttpServletRequest request) {
        super(request);
    }

    public static Request build() {
        return new Request(Context.get().getRequest());
    }

}
