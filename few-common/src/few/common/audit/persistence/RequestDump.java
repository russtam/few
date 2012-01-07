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
}
