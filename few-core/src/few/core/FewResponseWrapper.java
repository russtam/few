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
 * User: igor
 * Date: 23.01.12
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
