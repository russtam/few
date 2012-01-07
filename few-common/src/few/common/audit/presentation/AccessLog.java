package few.common.audit.presentation;

import few.ModelBean;
import few.common.audit.persistence.RequestDump;
import few.common.audit.service.AuditService;
import few.utils.ListWrapper;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 07.01.12
 * Time: 22:40
 * To change this template use File | Settings | File Templates.
 */
@ModelBean(name = "access_log")
public class AccessLog extends ListWrapper<RequestDump> {
    private AccessLog(List<RequestDump> requestDumps) {
        super(requestDumps);
    }

    private static AuditService auditService = AuditService.get();
    public static AccessLog build() {
        AccessLog ret = new AccessLog(auditService.selectRequestDump());
        return ret;
    }
}
