package few.sample;

import few.Context;
import few.core.Dispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * User: igor
 * Date: 31.01.12
 */
public class AccessDeniedPage extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(Context.get().isSignedIn()) {
            Dispatcher.get().processTemplate("/bem_pages/_errors/p_403.ftl", req, resp);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(req.getRequestURL());
            if(!req.getParameterMap().isEmpty())
                sb.append("?");
            for (Iterator iterator = req.getParameterMap().entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<String, String[]> e = (Map.Entry<String, String[]>) iterator.next();
                for (int i = 0; i < e.getValue().length; i++) {
                    String s = e.getValue()[i];
                    sb.append(e.getKey()).append("=").append(s);
                    if( iterator.hasNext() )
                        sb.append("&");
                }
            }
            String redirect = URLEncoder.encode(sb.toString(), "UTF-8");
            if( redirect.length() < 2000 )
                resp.sendRedirect("/login?redirect=" + redirect);
            else
                resp.sendRedirect("/login");
        }

    }
}
