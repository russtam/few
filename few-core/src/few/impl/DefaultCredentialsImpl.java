package few.impl;

import few.services.Credentials;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 19.12.11
 * Time: 1:22
 * To change this template use File | Settings | File Templates.
 */
public class DefaultCredentialsImpl implements Credentials {
    public String getUserID(HttpServletRequest request) {
        return null;
    }

    public boolean isSignedIn(HttpServletRequest request) {
        return false;
    }

    public Set<String> getRoles(HttpServletRequest request) {
        return Collections.emptySet();
    }
}
