package few.common.audit.dao;

import few.common.BaseMyBatisServiceImpl;
import few.common.audit.dto.ActivityEntry;
import few.common.audit.dto.RequestDump;
import few.common.audit.service.AuditKeys;
import few.utils.MapBuilder;

import java.util.List;

/**
 * User: igor
 * Date: 07.01.12
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

    public void insertActivity(Integer user_id, Integer level, String type, String text) {
        ActivityEntry ae = new ActivityEntry(user_id, level, type, text);
        session().insert("few.common.insertActivity", ae );
        session().commit();
    }

    public void insertActivity(Integer level, String type, String text) {
        ActivityEntry ae = new ActivityEntry(level, type, text);
        session().insert("few.common.insertActivity", ae );
        session().commit();
    }

    public List<ActivityEntry> selectActivities() {
        return session().selectList("few.common.selectActivities");
    }

    public List<ActivityEntry> selectActivities(int page, int pageSize) {
        return session().selectList("few.common.selectActivities",
                new MapBuilder().add("page_size", pageSize).add("page_number", page));
    }

    public Integer selectActivitiesCount() {
        return (Integer) session().selectOne("few.common.selectActivitiesCount");
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

    public List<RequestDump> selectRequestDump(int page, int pageSize) {
        return session().selectList("few.common.selectRequestDump",
                new MapBuilder().add("page_size", pageSize).add("page_number", page));
    }

    public void clearAccessLog() {
        session().delete("few.common.clearAccess");
        session().commit();
    }

    public Integer selectRequestDumpCount() {
        return (Integer) session().selectOne("few.common.selectRequestDumpCount");
    }
}
