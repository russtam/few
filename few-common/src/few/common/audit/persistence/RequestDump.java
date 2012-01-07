package few.common.audit.persistence;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 07.01.12
 * Time: 18:52
 * To change this template use File | Settings | File Templates.
 */
public class RequestDump {
    public String uri;
    public String method;
    public int response_code;
    public int processing_time;
    public long timestamp;

    public String remote_address;
    public String remote_host;
    public String referer;

    public String session_id;
    public String user_id;

    public String getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public int getResponse_code() {
        return response_code;
    }

    public int getProcessing_time() {
        return processing_time;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getRemote_address() {
        return remote_address;
    }

    public String getRemote_host() {
        return remote_host;
    }

    public String getReferer() {
        return referer;
    }

    public String getSession_id() {
        return session_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public Date getTime() {
        return new Date(timestamp);
    }
}
