package few.common.users.service;

import few.common.users.controller.LoginAction;
import few.common.users.dao.UserService;
import few.services.Credentials;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Set;

/**
 * User: igor
 * Date: 19.12.11
 */
public class CredentialsImpl implements Credentials{
    public String getUserID(HttpServletRequest request) {
        return getUserID(request.getSession());
    }

    public String getUserID(HttpSession session) {
        Integer id = (Integer) session.getAttribute(LoginAction.USER_ID_SESSION_KEY);
        if( id != null )
            return String.valueOf(id);
        return null;
    }

    public boolean isSignedIn(HttpServletRequest request) {
        return request.getSession().getAttribute(LoginAction.USER_ID_SESSION_KEY) != null;
    }

    private final static String ROLES_KEY = "roles";
    UserService userService = UserService.get();

    public Set<String> getRoles(HttpServletRequest request) {
        Set<String> ret = (Set<String>) request.getAttribute(ROLES_KEY);
        if( ret != null )
            return ret;
        String uid = getUserID(request);
        if( uid != null ) {
            ret = userService.selectUser(Integer.valueOf(uid)).roles;
            request.setAttribute(ROLES_KEY, ret);
            return ret;
        }
        return Collections.emptySet();
    }}
