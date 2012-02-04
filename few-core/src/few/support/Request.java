package few.support;

import few.Context;
import few.ModelBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * User: gerbylev
 * Date: 30.11.11
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
