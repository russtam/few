package few.common.audit.service;

import few.common.BaseTest;
import few.common.TestContext;
import few.common.audit.persistence.ActivityEntry;
import few.common.audit.persistence.RequestDump;
import few.core.ServiceRegistry;
import few.services.Credentials;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 07.01.12
 * Time: 19:31
 * To change this template use File | Settings | File Templates.
 */
public class AuditServiceTest extends BaseTest{

    AuditService auditService = AuditService.get();

    public void testActivitySimple() {

        auditService.insertActivity(1, "test", "message");

        List<ActivityEntry> ae = auditService.selectActivities();

        assertFalse(ae.isEmpty());

    }

    public void testRequestSimple() {
        RequestDump d = new RequestDump();

        d.uri = "/test";
        d.method = "GET";
        d.response_code = 200;
        d.processing_time = 300;
        d.timestamp = System.currentTimeMillis();

        d.remote_address = "127.0.0.1";
        d.remote_host = "localhost";
        d.referer = "google.com";

        d.session_id = "asda123asd";
        d.user_id = "1";

        auditService.insertRequestDump(d);

        List<RequestDump> list = auditService.selectRequestDump();

        assertFalse(list.isEmpty());
    }

}
