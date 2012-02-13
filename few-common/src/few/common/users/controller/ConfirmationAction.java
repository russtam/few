package few.common.users.controller;

import few.ActionResponse;
import few.Context;
import few.Message;
import few.common.audit.dao.AuditService;
import few.common.audit.service.AuditKeys;
import few.common.users.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * User: igor
 * Date: 13.02.12
 * Time: 11:52
 */
public class ConfirmationAction extends HttpServlet {

    AuditService auditService = AuditService.get();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        String key = req.getParameter("key");
        HttpSession session = req.getSession();

        if( action.equals("registration") ) {
            Integer user_id = AccountService.get().activateUser(key);
            if( user_id != null ) {
                Context.get().addMessage(new Message(Message.INFO, "Ваш аккаунт успешно подтверждён."));
                session.setAttribute(LoginAction.USER_ID_SESSION_KEY, user_id);
                auditService.insertActivity(
                        AuditKeys.NORMAL, AuditKeys.ACTIVATION, "");
                resp.sendRedirect("/user/profile");
            } else {
                Context.get().addMessage(new Message(Message.INFO, "Ссылка устарела."));
                resp.sendRedirect("/registration");
            }
        } else
        if( action.equals("restore_password") ) {
            if( AccountService.get().restorePasswordActivate(key) ) {
                Context.get().addMessage( new Message(Message.INFO, "Ваш новый пароль активирован, можете заходить.") );
                auditService.insertActivity(
                    AuditKeys.MINOR, AuditKeys.RESTORE_PASSWORD, "activate");
                resp.sendRedirect("/login");
            } else {
                Context.get().addMessage( new Message(Message.ERROR, "Ссылка устарела. Попробуйте запросить восстановление пароля ещё раз.") );
            }
            resp.sendRedirect("/restore_password");
        } else {
            resp.sendRedirect("/");
        }
    }
}
