package few.common.audit.controller;

import few.Controller;
import few.Action;
import few.ActionResponse;
import few.common.audit.dao.AuditService;
import few.common.audit.service.AuditKeys;

/**
 * User: igor
 * Date: 07.01.12
 */
@Controller(name = "audit", permission = "admin")
public class AuditController {

    private static AuditService auditService = AuditService.get();
    @Action
    public ActionResponse clearAccessLog() {
        auditService.clearAccessLog();
        return ActionResponse.referer();
    }

    @Action
    public ActionResponse clearActivity() {
        auditService.clearActivities();
        auditService.insertActivity(AuditKeys.WARNING, AuditKeys.CLEAR_ACTIVITES);
        return ActionResponse.referer();
    }
}
