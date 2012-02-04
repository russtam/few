package few.impl;

import few.services.Credentials;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Set;

/**
 * User: igor
 * Date: 19.12.11
 */
public class DefaultCredentialsImpl implements Credentials {
    public String getUserID(HttpServletRequest request) {
        return null;
    }

    public String getUserID(HttpSession session) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isSignedIn(HttpServletRequest request) {
        return false;
    }

    public Set<String> getRoles(HttpServletRequest request) {
        return Collections.emptySet();
    }
}
