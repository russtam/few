package few.common.audit.service;

import few.common.BaseMyBatisServiceImpl;
import few.common.audit.persistence.ActivityEntry;
import few.common.audit.persistence.RequestDump;
import few.utils.MapBuilder;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 07.01.12
 * Time: 13:07
 * To change this template use File | Settings | File Templates.
 */
public class AuditService extends BaseMyBatisServiceImpl {

    private static AuditService instance = new AuditService();
    public static AuditService get() {
        return instance;
    }
    private AuditService() {
    }

    public void insertActivity(Integer level, String type, String text) {
        ActivityEntry ae = new ActivityEntry(level, type, text);
        session().insert("few.common.insertActivity", ae );
        session().commit();
    }

    public List<ActivityEntry> selectActivities() {
        return session().selectList("few.common.selectActivities", null);
    }


    public void insertRequestDump(RequestDump requestDump) {
        session().insert("few.common.insertRequestDump", requestDump);
        session().commit();
    }

    public List<RequestDump> selectRequestDump() {
        return session().selectList("few.common.selectRequestDump");
    }
}
