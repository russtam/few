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
    public static final int REDIRECT = 1;
    public static final int ERROR    = 2;
    public static final int JSON     = 3;

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
        return new ActionResponse(REDIRECT, "/" + where);
    }

    public static ActionResponse referer() {
        String referer = Context.get().getRequest().getHeader("referer");
        if( referer != null )
            return new ActionResponse(REDIRECT, referer);
        else
            return new ActionResponse(REDIRECT, "/");
    }

    public static ActionResponse redirect(String where) {
        return new ActionResponse(REDIRECT, where);
    }

    public static ActionResponse redirect(MyURL where) {
        return new ActionResponse(REDIRECT, where.toString());
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

}
