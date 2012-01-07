package few.common.users.controller;

import few.*;
import few.common.audit.service.AuditKeys;
import few.common.audit.service.AuditService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

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

    public static AuditService auditService = AuditService.get();
    @ActionMethod(_default = true)
    public ActionResponse service(HttpSession session) throws ServletException, IOException {

        Enumeration<String> e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            String s = e.nextElement();
            session.removeAttribute(s);
        }
        auditService.insertActivity(
                AuditKeys.NORMAL, AuditKeys.LOGOUT, "");


        return ActionResponse.redirect(new MyURL(false, "/"));
    }

}
