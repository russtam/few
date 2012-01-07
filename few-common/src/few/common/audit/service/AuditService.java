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

    public void insertActivity(String type, String text) {
        insertActivity(AuditKeys.NORMAL, type, text);
    }
    public void insertActivity(String type) {
        insertActivity(AuditKeys.NORMAL, type, "");
    }
    public void insertActivity(Integer level, String type) {
        insertActivity(level, type, "");
    }

    public void insertActivity(Integer level, String type, String text) {
        ActivityEntry ae = new ActivityEntry(level, type, text);
        session().insert("few.common.insertActivity", ae );
        session().commit();
    }

    public List<ActivityEntry> selectActivities() {
        return session().selectList("few.common.selectActivities", null);
    }

    public void clearActivities() {
        session().delete("few.common.clearActivities");
        session().commit();
    }


    public void insertRequestDump(RequestDump requestDump) {
        session().insert("few.common.insertRequestDump", requestDump);
        session().commit();
    }

    public List<RequestDump> selectRequestDump() {
        return session().selectList("few.common.selectRequestDump");
    }

    public void clearAccessLog() {
        session().delete("few.common.clearAccess");
        session().commit();
    }
}
