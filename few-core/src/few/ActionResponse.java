package few;

import few.core.Dispatcher;
import few.utils.JSONRenderer;

import java.net.MalformedURLException;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 05.11.11
 * Time: 11:47
 * To change this template use File | Settings | File Templates.
 */
public class ActionResponse {

    public static final int DEFAULT  = 0;
    public static final int FORWARD  = 1;
    public static final int REDIRECT = 2;
    public static final int ACTION   = 3;
    public static final int ERROR    = 4;
    public static final int JSON     = 5;

    private int error_code;
    private int response_type;

    private String key;

    private ActionResponse(int response_type, String key) {
        this.response_type = response_type;
        this.key = key;
    }

    private ActionResponse(int error_code) {
        this.response_type = ERROR;
        this.error_code = error_code;
    }

    public static ActionResponse _default() {
        return new ActionResponse(DEFAULT, null);
    }

    public static ActionResponse view(String where) {
        checkPage(where);
        if( "GET".equals( Context.get().getRequest().getMethod() ) )
            return new ActionResponse(FORWARD, where);
        else
            return new ActionResponse(REDIRECT, "/" + where);
    }

    public static ActionResponse redirect(String where) {
        return new ActionResponse(REDIRECT, where);
    }

    public static ActionResponse redirect(MyURL where) {
        return new ActionResponse(REDIRECT, where.toString());
    }

    public static ActionResponse action(String which) {
        return new ActionResponse(ACTION, which);
    }

    public static ActionResponse json(Object res) {
        return new ActionResponse(JSON, JSONRenderer.buildJSON(res));
    }

    public static ActionResponse error(int code) {
        return new ActionResponse(code);
    }

    public int getError_code() {
        return error_code;
    }

    public int getResponse_type() {
        return response_type;
    }

    public String getKey() {
        return key;
    }


    private static void checkPage(String where) {
        String ftl= Dispatcher.BASE_PAGE_PATH + where + ".ftl";
        try {
            if( Context.get().getServletContext().getResource(ftl) == null ) {
                throw new IllegalArgumentException("page " + where + " does not exists");
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }

    }


}
