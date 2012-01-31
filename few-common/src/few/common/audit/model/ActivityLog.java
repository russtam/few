package few.common.audit.model;

import few.ModelBean;
import few.common.audit.dao.AuditService;
import few.common.audit.dto.ActivityEntry;
import few.utils.ListWrapper;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 07.01.12
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
@ModelBean(name = "activity_log", permission = "admin")
public class ActivityLog extends ListWrapper<ActivityEntry>{


    private ActivityLog(List<ActivityEntry> activityEntries) {
        super(activityEntries);
    }

    private static AuditService auditService = AuditService.get();
    public static ActivityLog build() {
        ActivityLog ret = new ActivityLog(auditService.selectActivities());
        return ret;
    }
}