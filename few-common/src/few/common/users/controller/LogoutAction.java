package few.common.users.controller;

import few.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 31.10.11
 * Time: 19:05
 * To change this template use File | Settings | File Templates.
 */
@ActionClass(action = "logout")
@Restriction(roles = "user")
public class LogoutAction {

    @ActionMethod(_default = true)
    public ActionResponse service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getSession().removeAttribute(LoginAction.USER_ID_SESSION_KEY);

        return ActionResponse.redirect(new MyURL(false, "/"));
    }

}
