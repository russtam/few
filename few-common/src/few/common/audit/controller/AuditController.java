package few.common.audit.controller;

import few.Controller;
import few.Action;
import few.ActionResponse;
import few.RequestParameter;
import few.common.audit.service.AuditKeys;
import few.common.audit.service.AuditService;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 07.01.12
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
@Controller(name = "audit")
public class AuditController {

    @Action(_default = true)
    public ActionResponse _default() {
        return ActionResponse.redirect("/");
    }

    private static AuditService auditService = AuditService.get();
    @Action
    public ActionResponse clearAccessLog(@RequestParameter(name = "clear_access") String action) {
        auditService.clearAccessLog();
        return ActionResponse.referer();
    }

    @Action
    public ActionResponse clearActivity(@RequestParameter(name = "clear_activities") String action) {
        auditService.clearActivities();
        auditService.insertActivity(AuditKeys.WARNING, AuditKeys.CLEAR_ACTIVITES);
        return ActionResponse.referer();
    }
}
