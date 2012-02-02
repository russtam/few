package few.common.users.model;

import few.Context;
import few.ModelBean;
import few.MyURL;
import few.common.PropKeys;
import few.core.ServiceRegistry;
import few.services.Configuration;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 31.10.11
 * Time: 23:50
 * To change this template use File | Settings | File Templates.
 */
@ModelBean(name = "loginForm")
public class LoginForm {
    private String loginUrl;

    public String getLoginUrl() {
        return loginUrl;
    }

    private static Configuration configuration = ServiceRegistry.get(Configuration.class);
    public static LoginForm build() {
        LoginForm ret = new LoginForm();
        boolean https = Boolean.parseBoolean(configuration.getProperty(PropKeys.LOGIN_THROUGH_HTTPS));
        MyURL mu = new MyURL(https, "/login.login");
        if( Context.get().getRequest().getParameter("redirect") != null )
            mu.p("redirect", Context.get().getRequest().getParameter("redirect"));
        ret.loginUrl = mu.toString();

        return ret;
    }

}
