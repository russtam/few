package few.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * User: gerbylev
 * Date: 29.11.11
 */
public interface Credentials {

    String getUserID(HttpServletRequest request);

    String getUserID(HttpSession session);

    boolean isSignedIn(HttpServletRequest request);

    Set<String> getRoles(HttpServletRequest request);

}
