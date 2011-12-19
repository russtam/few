package few.common.users.service;

import few.common.users.controller.LoginAction;
import few.services.Credentials;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 19.12.11
 * Time: 2:27
 * To change this template use File | Settings | File Templates.
 */
public class CredentialsImpl implements Credentials{
    public String getUserID(HttpServletRequest request) {
        return String.valueOf( (Integer) request.getSession().getAttribute(LoginAction.USER_ID_SESSION_KEY) );
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
