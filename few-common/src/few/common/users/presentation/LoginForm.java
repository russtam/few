package few.common.users.presentation;

import few.Context;
import few.ModelBean;
import few.MyURL;
import few.common.users.controller.LoginAction;
import few.utils.Utils;

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

    public static LoginForm build() {
        LoginForm ret = new LoginForm();
        MyURL mu = new MyURL(true, "/login.login");
        if( Context.get().getRequest().getParameter("redirect") != null )
            mu.p("redirect", Context.get().getRequest().getParameter("redirect"));
        ret.loginUrl = mu.toString();

        return ret;
    }

}
