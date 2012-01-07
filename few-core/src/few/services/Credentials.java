package few.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 29.11.11
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public interface Credentials {

    String getUserID(HttpServletRequest request);

    String getUserID(HttpSession session);

    boolean isSignedIn(HttpServletRequest request);

    Set<String> getRoles(HttpServletRequest request);

}
