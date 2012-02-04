package few.common.audit.model;

import few.ModelBean;
import few.RequestParameter;
import few.common.audit.dao.AuditService;
import few.common.audit.dto.RequestDump;

import java.util.List;

/**
 * User: igor
 * Date: 07.01.12
 */
@ModelBean(name = "access_log")
public class AccessLog extends BasePaginator<RequestDump> {

    private AccessLog(List<RequestDump> requestDumps, int page, int pageSize) {
        super(requestDumps, page, pageSize);
    }

    @Override
    protected int _recordCount() {
        return auditService.selectRequestDumpCount();
    }

    private static AuditService auditService = AuditService.get();

    public static AccessLog build(
            @RequestParameter(name = "page_number", required = false) Integer page,
            @RequestParameter(name = "page_size", required = false) Integer size
    ) {
        if( page == null )
            return new AccessLog(auditService.selectRequestDump(), -1, -1);
        else {
            if( size == null )
                size = 100;
            return new AccessLog(auditService.selectRequestDump(page, size), page, size);
        }
    }

}
