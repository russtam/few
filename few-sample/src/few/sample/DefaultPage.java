package few.sample;

import few.Context;
import few.Controller;
import few.Action;
import few.ActionResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 24.12.11
 * Time: 0:13
 * To change this template use File | Settings | File Templates.
 */
public class DefaultPage extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if( !Context.get().isSignedIn() ) {
            resp.sendRedirect("/login");
        } else {
            if( Context.get().hasPermission("admin") )
                resp.sendRedirect("/admin/user_list");
            else
                resp.sendRedirect("/user/profile");
        }
    }
}
