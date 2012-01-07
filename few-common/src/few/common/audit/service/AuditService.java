package few.common.audit.service;

import few.common.BaseMyBatisServiceImpl;
import few.common.audit.persistence.ActivityEntry;
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

    public void insertActivity(Integer user_id, Integer level, String type, String text) {
        session().insert("insertActivity", new MapBuilder()
                .add("user_id", user_id)
                .add("level", level)
                .add("type", type)
                .add("text", text)
                );
        session().commit();
    }

    public List<ActivityEntry> selectActivities() {
        return session().selectList("selectActivities", null);
    }



}
