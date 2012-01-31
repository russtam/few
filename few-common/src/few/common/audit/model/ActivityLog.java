package few.common.audit.model;

import few.ModelBean;
import few.RequestParameter;
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
public class ActivityLog extends BasePaginator<ActivityEntry>{


    private ActivityLog(List<ActivityEntry> activityEntries, int page, int pageSize) {
        super(activityEntries, page, pageSize);
    }

    @Override
    protected int _recordCount() {
        return auditService.selectRequestDumpCount();
    }

    private static AuditService auditService = AuditService.get();

    public static ActivityLog build(
            @RequestParameter(name = "page_number", required = false) Integer page,
            @RequestParameter(name = "page_size", required = false) Integer size
    ) {
        if( page == null )
            return new ActivityLog(auditService.selectActivities(), -1, -1);
        else {
            if( size == null )
                size = 100;
            return new ActivityLog(auditService.selectActivities(page, size), page, size);
        }
    }

}
