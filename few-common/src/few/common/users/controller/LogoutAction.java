package few.common.users.controller;

import few.*;
import few.common.audit.dao.AuditService;
import few.common.audit.service.AuditKeys;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * User: gerbylev
 * Date: 31.10.11
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
