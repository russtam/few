package few.common.audit.dto;

import java.util.Date;

/**
 * User: igor
 * Date: 07.01.12
 */
public class RequestDump {
    public String uri;
    public String method;
    public int response_code;
    public int processing_time;
    public long timestamp;

    public String remote_address;
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
