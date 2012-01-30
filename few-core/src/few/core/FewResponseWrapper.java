package few.core;

import few.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 23.01.12
 * Time: 8:41
 * To change this template use File | Settings | File Templates.
 */
public class FewResponseWrapper extends HttpServletResponseWrapper {

    HttpServletRequest request;
    public FewResponseWrapper(HttpServletRequest request, HttpServletResponse response) {
        super(response);
        this.request = request;
    }

    public void sendError(int sc) throws IOException {
        sendError(sc);
    }

    boolean error_processing = false;
    public void sendError(int sc, String msg) throws IOException {
        // avoid recursion
        if( error_processing ) {
            super.sendError(sc, msg);
            return;
        }
        error_processing = true;

        Dispatcher.get().processError(sc, msg, request, this);
    }
}
