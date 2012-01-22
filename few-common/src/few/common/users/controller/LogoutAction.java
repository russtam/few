package few.common.users.controller;

import few.*;
import few.common.audit.service.AuditKeys;
import few.common.audit.service.AuditService;

import javax.servlet.ServletException;
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
@Controller(name = "logout", permission = "user")
public class LogoutAction {

    public static AuditService auditService = AuditService.get();

    @Action()
    public ActionResponse logout(HttpSession session) throws ServletException, IOException {
        auditService.insertActivity(
                AuditKeys.NORMAL, AuditKeys.LOGOUT, "");

        Enumeration<String> e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            String s = e.nextElement();
            session.removeAttribute(s);
        }

        return ActionResponse.redirect(new MyURL(false, "/"));
    }

}
